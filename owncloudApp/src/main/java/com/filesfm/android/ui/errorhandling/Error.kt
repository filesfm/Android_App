package com.filesfm.android.ui.errorhandling

import com.filesfm.android.lib.common.operations.RemoteOperationResult.ResultCode

data class Error(var code: ResultCode, var exception: Exception)