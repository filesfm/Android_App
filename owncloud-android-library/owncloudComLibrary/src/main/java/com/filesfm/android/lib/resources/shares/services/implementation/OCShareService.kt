/**
 * ownCloud Android client application
 *
 * @author David González Verdugo
 *
 * Copyright (C) 2020 ownCloud GmbH.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.filesfm.android.lib.resources.shares.services.implementation

import com.filesfm.android.lib.common.OwnCloudClient
import com.filesfm.android.lib.common.operations.RemoteOperationResult
import com.filesfm.android.lib.resources.shares.CreateRemoteShareOperation
import com.filesfm.android.lib.resources.shares.GetRemoteSharesForFileOperation
import com.filesfm.android.lib.resources.shares.RemoveRemoteShareOperation
import com.filesfm.android.lib.resources.shares.ShareParserResult
import com.filesfm.android.lib.resources.shares.ShareType
import com.filesfm.android.lib.resources.shares.UpdateRemoteShareOperation
import com.filesfm.android.lib.resources.shares.services.ShareService

class OCShareService(override val client: OwnCloudClient) :
    ShareService {
    override fun getShares(
        remoteFilePath: String,
        reshares: Boolean,
        subfiles: Boolean
    ): RemoteOperationResult<ShareParserResult> = GetRemoteSharesForFileOperation(
        remoteFilePath,
        reshares,
        subfiles
    ).execute(client)

    override fun insertShare(
        remoteFilePath: String,
        shareType: ShareType,
        shareWith: String,
        permissions: Int,
        name: String,
        password: String,
        expirationDate: Long,
        publicUpload: Boolean
    ): RemoteOperationResult<ShareParserResult> =
        CreateRemoteShareOperation(
            remoteFilePath,
            shareType,
            shareWith,
            permissions
        ).apply {
            this.name = name
            this.password = password
            this.expirationDateInMillis = expirationDate
            this.publicUpload = publicUpload
            this.retrieveShareDetails = true
        }.execute(client)

    override fun updateShare(
        remoteId: Long,
        name: String,
        password: String?,
        expirationDate: Long,
        permissions: Int,
        publicUpload: Boolean
    ): RemoteOperationResult<ShareParserResult> =
        UpdateRemoteShareOperation(
            remoteId
        ).apply {
            this.name = name
            this.password = password
            this.expirationDateInMillis = expirationDate
            this.permissions = permissions
            this.publicUpload = publicUpload
            this.retrieveShareDetails = true
        }.execute(client)

    override fun deleteShare(remoteId: Long): RemoteOperationResult<ShareParserResult> =
        RemoveRemoteShareOperation(
            remoteId
        ).execute(client)
}
