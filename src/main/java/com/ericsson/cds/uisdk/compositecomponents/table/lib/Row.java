package com.ericsson.cds.uisdk.compositecomponents.table.lib;

import java.util.List;

/**
 * UI SDK table row
 */
public interface Row<T extends Cell> {

    List<T> getCells();

    T getCell(int cellIndex);
}
