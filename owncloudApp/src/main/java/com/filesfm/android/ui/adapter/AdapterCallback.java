package com.filesfm.android.ui.adapter;

import android.view.MenuItem;

import com.filesfm.android.datamodel.OCFile;

//Files.fm completely new class to get info from FileListListAdapter to OCFileListFragment
public interface AdapterCallback {
    boolean onMethodCallback(OCFile file, MenuItem menuItem);
}
