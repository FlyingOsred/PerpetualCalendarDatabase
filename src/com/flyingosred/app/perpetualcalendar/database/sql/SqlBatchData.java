/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.sql;

import java.sql.PreparedStatement;

public interface SqlBatchData {

    public boolean setValues(PreparedStatement ps, int i);

    public int getSize();

}
