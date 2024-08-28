# Dropdown Example

This example is based on this 
[UI SDK Dropdown example](https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/widgets/latest/showcase/index.html#ui-showcase/selectors/dropdown)

    public class AppViewModel extends GenericViewModel {
        
        @UiComponentMapping(selector = ".ebComponentList.eb_scrollbar.elWidgets-ComponentList.ebComponentList_focus_forced")
        private UiSdkDropDownMenu menu;
        
        ....
        
        public boolean createEntry(){
            ...
            menu.clickOptionByName("Create");
            ...
        }
        ....        
    }
    
    public class ApplicationOperatorUiImpl implements ApplicationOperator {
        
        private AppViewModel tableViewModel;
        
        //....
          
        public void testCRUD(){
            
            appViewModel = browser.getView(AppViewModel.class);
            ....
            boolean entryCreated = appViewModel.createEntry();
            ...
        }
    }
    