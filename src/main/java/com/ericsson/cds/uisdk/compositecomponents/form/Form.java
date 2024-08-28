package com.ericsson.cds.uisdk.compositecomponents.form;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.core.UiComponentNotFoundException;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Lists;

/**
 * Sample control for dealing with UI SDK Form.
 *
 * @author ehrvkla
 * @since 1.0.25
 * @see <a href=
 *       "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/layouts/latest/examples/#form-example">
 *       Form Flyout Example</a>
 */
public class Form extends AbstractUiComponent {

    /** The field errors. */
    @UiComponentMapping(selector = ".ebFieldNotification_show_error")
    protected List<UiComponent> fieldErrors;

    /** The fields. */
    @UiComponentMapping(selector = ".elLayouts-Form-Field,.elLayouts-Form-Struct")
    protected List<FormField> fields;

    /**
     * Set empty or default value to fields.
     * <br>
     * Note: sets Switcher to false, Spinner to 0, remove all repeatable fields.
     * 
     * @param fields the fieldNames or fieldLabels
     */
    public void clear(String... fields) {
        for (String fieldKey : fields) {
            getFieldByKey(fieldKey).clear();
        }
    }

    /**
     * Set empty or default value to fields.
     * <br>
     * Note: sets Switcher to false, Spinner to 0, remove all repeatable fields.
     * 
     * @param fields the fieldNames or fieldLabels
     */
    public void clearAll() {
        // NOTE- reverse cleanup is used to prevent problem when clearing one field affect/change below form field XPATH
        for (FormField field : Lists.reverse(getFormFields())) {
            try {
                field.clear();
            } catch (UnsupportedOperationException ignore) {} // ignore exceptions for not supported field types
        }
    }

    /**
     * Checks if form is valid (if no field errors are displayed).
     *
     * @return true, if is valid
     */
    public boolean isValid() {
        return fieldErrors.isEmpty();
    }

    /**
     * Sets the values.
     * If form filling order is important, use {@link java.util.LinkedHashMap}
     *
     * @param map the map that contains fieldName:value or fieldLabel-value elements
     *        <pre>
     * "selectbox":, "Item 1",
     * "username": "test username"
     * </pre>
     *        For repeatable structures, use indexes (starts with 1) in brackets e.g. :
     *        <pre>
     * "input[1]" : "item value"
     * "structure.nested_structure[2].inputfield" : "input field value"
     * </pre>
     *        Null values are ignored, not filled.
     *        For form fields that has multiple values, use {@link #setValues(Multimap)}
     */
    public void setValues(Map<String, String> map) {
        Multimap<String, String> multimap = LinkedListMultimap.create(); // just copy all items to new MultiMap
        for (String key : map.keySet()) {
            multimap.put(key, map.get(key));
        }
        setValues(multimap);
    }

    /**
     * Sets the values.
     * If form filling order is important, use {@link com.google.common.collect.LinkedListMultimap}
     * 
     * @param multiMap the multi map that contains all fieldPath-values that should be filled, e.g.
     *        <pre>
     * "multiselect":, "Item 0",
     * "multiselect":, "Item 1",
     * "username": "test username"
     * </pre>
     *        For repeatable structures, use indexes (starts with 1) in brackets e.g. :
     *        <pre>
     * "input[1]" : "item value"
     * "structure.nested_structure[2].inputfield" : "input field value"
     * </pre>
     *        Null values are ignored, not filled.
     */
    public void setValues(Multimap<String, String> multiMap) {
        for (String fieldPath : multiMap.keySet()) {
            getFieldByKey(fieldPath).setValue(multiMap.get(fieldPath));
        }
    }

    /**
     * Ensure structure field.
     *
     * @param structureFieldPath the structure field path
     * @param subForm the sub form
     * @param originalFieldPath the original field path
     * @return the form field
     */
    protected FormField ensureStructureField(String structureFieldPath, Form subForm, String originalFieldPath) {
        if (originalFieldPath == null) {// initial recursion level
            originalFieldPath = structureFieldPath;
            subForm = this;
        }

        // handle field paths with index- e.g. structure[1].field
        Matcher matcher = Pattern.compile("\\[\\d+\\]").matcher(structureFieldPath);
        if (matcher.find()) {
            final String RESULT = matcher.group(0);

            // get left(parent) and right(child) subpath
            final String PATH_LEFT = structureFieldPath.substring(0, structureFieldPath.indexOf(RESULT)).replace(RESULT,
                    "");
            String pathRight = structureFieldPath.substring(structureFieldPath.indexOf(RESULT) + RESULT.length());
            if (pathRight.startsWith(".")) {
                pathRight = pathRight.substring(1);
            }

            RepeatableFormField repeatableFormField = subForm.getFieldsByDataRepeatable(PATH_LEFT);
            if (repeatableFormField == null) {
                throw new UiComponentNotFoundException("Can't find repeatable structure's field '" + PATH_LEFT + "'.");
            }
            FormField ensuredField = repeatableFormField
                    .ensureFieldAtIndex(getStructFieldIndex(structureFieldPath, RESULT));
            Form nextSubForm;
            if (ensuredField instanceof FormStructure) {
                nextSubForm = ((FormStructure) ensuredField).getSubForm();
            } else {
                nextSubForm = repeatableFormField.getSubForm();
            }

            if (pathRight.isEmpty()) {
                return ensuredField; // if no sub fields
            } else {
                final String fieldPathReplaced = PATH_LEFT + "." + pathRight;
                return ensureStructureField(fieldPathReplaced, nextSubForm, originalFieldPath);
            }
        }

        FormField ensuredField = subForm.getFieldsByDataPath(structureFieldPath);
        if (ensuredField == null) {
            throw new UiComponentNotFoundException(
                    "Problem while ensuring form structure field with path " + originalFieldPath);
        }
        return ensuredField;
    }

    /**
     * Gets the field by key. (data-path element property or label)
     *
     * @param fieldKey data-path element property or label
     * @return the FormField
     */
    protected FormField getFieldByKey(String fieldKey) {

        // find field by data-path property
        FormField formField = getFieldsByDataPath(fieldKey);
        if (formField != null) {
            return formField;
        }

        // find field by data-repeatable property
        RepeatableFormField repeatable = getFieldsByDataRepeatable(fieldKey);
        if (repeatable != null) {
            return repeatable;
        }

        // find field structure
        FormStructure structure = getFormStructure(fieldKey);
        if (structure != null) {
            return structure;
        }

        throw new UiComponentNotFoundException("Can't find field with path '" + fieldKey + "'.");
    }

    /**
     * Gets the fields by data path.
     *
     * @param dataPath the data path
     * @return the fields by data path
     */
    protected FormField getFieldsByDataPath(String dataPath) {

        // if path contains index e.g. input[2]
        if (dataPath.contains("[")) {
            return ensureStructureField(dataPath, null, null);
        } else {
            List<UiComponent> elements = getDescendantsBySelector(SelectorType.XPATH,
                    "//*[@data-path='" + dataPath
                            + "' and @data-body='field']/ancestor::div[contains(@class, 'elLayouts-Form-Field')]");
            return elements.isEmpty() ? null : elements.get(0).as(FormField.class).expandParentElements();
        }
    }

    /**
     * Gets the fields by data repeatable.
     *
     * @param dataRepeatable the data repeatable
     * @return the fields by data repeatable
     */
    protected RepeatableFormField getFieldsByDataRepeatable(String dataRepeatable) {
        List<UiComponent> elements = getDescendantsBySelector(SelectorType.XPATH,
                "//*[(contains(@class, 'elLayouts-Form-Field') or contains(@class, 'elLayouts-Form-Struct')) and @data-repeatable='"
                        + dataRepeatable + "']");

        return elements.isEmpty() ? null
                : elements.get(0).as(RepeatableFormField.class).expandParentElements().as(RepeatableFormField.class);
    }

    /**
     * Gets the form fields.
     *
     * @return the form fields
     */
    protected List<FormField> getFormFields() {
        return fields;
    }

    /**
     * Gets the form structure.
     *
     * @param dataPath the data path
     * @return the form structure
     */
    protected FormStructure getFormStructure(String dataPath) {
        List<UiComponent> elements = getDescendantsBySelector(SelectorType.XPATH,
                "//*[@data-path='" + dataPath
                        + "']/*[contains(@class, 'elLayouts-Form-StructBody')]/ancestor::div[@class='elLayouts-Form-Struct']");
        return elements.isEmpty() ? null
                : elements.get(0).as(FormStructure.class).expandParentElements().as(FormStructure.class);
    }

    /**
     * Gets the form structure data repeatable.
     *
     * @param dataRepeatable the data repeatable
     * @return the form structure data repeatable
     */
    protected RepeatableFormField getFormStructureDataRepeatable(String dataRepeatable) {
        List<UiComponent> elements = getDescendantsBySelector(SelectorType.XPATH,
                "//*[@data-repeatable='" + dataRepeatable
                        + "']/*[contains(@class, 'elLayouts-Form-Struct-body')]/ancestor::div[@class='elLayouts-Form-Struct']");

        return elements.isEmpty() ? null
                : elements.get(0).as(RepeatableFormField.class).expandParentElements().as(RepeatableFormField.class);
    }

    /**
     * Gets the struct field index.
     *
     * @param structureFieldPath the structure field path
     * @param result the result
     * @return the struct field index
     */
    private int getStructFieldIndex(String structureFieldPath, String result) {
        final int INDEX;
        try {
            INDEX = Integer.parseInt(result.replace("[", "").replace("]", ""));
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException(
                    "Field path index must be a number! Field path " + structureFieldPath);
        }
        if (INDEX <= 0) {
            throw new UnsupportedOperationException(
                    "Field path index starts with 1, received " + INDEX);
        }
        return INDEX;
    }
}
