package com.filesfm.android.data.server.datasources

import com.filesfm.android.domain.server.model.AuthenticationMethod
import com.filesfm.android.lib.resources.status.OwnCloudVersion

interface RemoteServerInfoDataSource {

    fun getAuthenticationMethod(path: String): AuthenticationMethod

    /**
     * Returns a Pair<OwncloudVersion, isSSLConnection>
     */
    fun getRemoteStatus(path: String): Pair<OwnCloudVersion, Boolean>
}
