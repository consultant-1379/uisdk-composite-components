package com.ericsson.cds.uisdk.compositecomponents.editablefield;

import com.ericsson.cds.uisdk.AbstractWidgetLibraryTest;
import com.ericsson.cds.uisdk.compositecomponents.editablefield.EditableField;
import com.ericsson.cds.uisdk.compositecomponents.test.viewmodels.UiSdkEditableFieldViewModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditableFieldTest extends AbstractWidgetLibraryTest {

    private EditableField editableField;
    private UiSdkEditableFieldViewModel editableFieldView;

    @Before
    public void initializeView() {
        editableFieldView = tab.getView(UiSdkEditableFieldViewModel.class);
        editableField = editableFieldView.getEditableField();
        editableFieldView.ensurePageIsLoaded(tab);
    }

    @Test
    public void getText() {
        String text = editableField.getText();
        assertEquals("This is editable field", text);
    }

    @Test
    public void setText() {
        final String text = "test text";
        editableField.setText(text);

        String textFromField = editableField.getText();
        assertEquals(text, textFromField);
    }

    @Test
    public void clearText() {
        editableField.clear();

        String textFromField = editableField.getText();
        assertEquals("", textFromField);
    }

    @Override
    protected String getWidgetUrl() {
        return "https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/form-elements/editable-field";
    }
}
