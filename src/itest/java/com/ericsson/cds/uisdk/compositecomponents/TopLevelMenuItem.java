package com.ericsson.cds.uisdk.compositecomponents;

import com.ericsson.cifwk.taf.ui.core.AbstractUiComponent;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.Link;

import java.util.List;

public class TopLevelMenuItem extends AbstractUiComponent{

    @UiComponentMapping(selector = ".elShowcaseLib-wMenuItem-link")
    private List<Link> subMenuOptions;

    public void clickWidgetShowcaseFor(String optionName){
        for(Link option: subMenuOptions){
            if(option.getText().equalsIgnoreCase(optionName)){
                option.click();
            }
        }
    }
}
