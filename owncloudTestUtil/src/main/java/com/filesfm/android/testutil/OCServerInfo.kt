package com.filesfm.android.testutil

import com.filesfm.android.domain.server.model.AuthenticationMethod
import com.filesfm.android.domain.server.model.ServerInfo

const val OC_BASE_URL = "https://demo.owncloud.com"

val OC_SERVER_INFO = ServerInfo(
    authenticationMethod = AuthenticationMethod.BASIC_HTTP_AUTH,
    baseUrl = "https://demo.owncloud.com",
    ownCloudVersion = "10.3.2.1",
    isSecureConnection = false
)
