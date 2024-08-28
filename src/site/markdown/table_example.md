# Table Example

This example is based on this 
[UI SDK Table example](https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/sites/tor/tablelib/latest/examples/#example3)

    public class UiSdkTableLibViewModel extends GenericViewModel {
        
        @UiComponentMapping(".elTablelib-Table")
        private Table table;
        
        public boolean sortByColumn(int columnIndex){
            return table.sortByColumn(columnIndex);
        }
        
        public String getCellValue(int rowIndex, int columnIndex){
            return table.getCellValue(rowIndex, columnIndex);
        }
    }
    
    public class ApplicationOperatorUiImpl implements ApplicationOperator {
        
        private UiSdkTableLibViewModel tableViewModel;
        
        //....
        
        
        public void dostuffWithTable(){
            
            tableViewModel = browser.getView(UiSdkTableViewModel.class);
            ...
            tableViewModel.sortByColumn(3);
            ....
            String cellValue = tableViewMode.getCellValue(6, 3);
            ...
        }
    }
    