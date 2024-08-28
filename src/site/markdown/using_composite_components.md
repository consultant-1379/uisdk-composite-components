# Using Composite Components

### Maven Dependency

Include the following dependency in the pom of your testware module (ERICTAF...CXP...)

    <dependency>
        <groupId>com.ericsson.cds</groupId>
        <artifactId>uisdk-composite-components</artifactId>
        <version>1.0.1</version>
    </dependency>

Please check 
[Nexus](https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/index.html#nexus-search;quick~uisdk-composite-components) 
for the latest version.

### In code

As with any component create an instance variable, and use the @UiComponentMapping annotation to map it to 
an element on the UI.

    public class UiSdkTableLibViewModel extends GenericViewModel {
    
        @UiComponentMapping(".elTablelib-Table")
        private TableLib table;
    
        public boolean sortTableByColumn(int columnIndex){
            return table.sortByColumn(columnIndex);
        }
        
        ...
    }
