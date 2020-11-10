/**
 * ownCloud Android client application
 *
 * @author David González Verdugo
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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.filesfm.android.data

import com.filesfm.android.domain.exceptions.AccountException
import com.filesfm.android.domain.exceptions.AccountNotFoundException
import com.filesfm.android.domain.exceptions.AccountNotNewException
import com.filesfm.android.domain.exceptions.AccountNotTheSameException
import com.filesfm.android.domain.exceptions.BadOcVersionException
import com.filesfm.android.domain.exceptions.CancelledException
import com.filesfm.android.domain.exceptions.ConflictException
import com.filesfm.android.domain.exceptions.CopyIntoDescendantException
import com.filesfm.android.domain.exceptions.DelayedForWifiException
import com.filesfm.android.domain.exceptions.FileNotFoundException
import com.filesfm.android.domain.exceptions.ForbiddenException
import com.filesfm.android.domain.exceptions.IncorrectAddressException
import com.filesfm.android.domain.exceptions.InstanceNotConfiguredException
import com.filesfm.android.domain.exceptions.InvalidCharacterException
import com.filesfm.android.domain.exceptions.InvalidCharacterInNameException
import com.filesfm.android.domain.exceptions.InvalidLocalFileNameException
import com.filesfm.android.domain.exceptions.InvalidOverwriteException
import com.filesfm.android.domain.exceptions.LocalFileNotFoundException
import com.filesfm.android.domain.exceptions.LocalStorageFullException
import com.filesfm.android.domain.exceptions.LocalStorageNotCopiedException
import com.filesfm.android.domain.exceptions.LocalStorageNotMovedException
import com.filesfm.android.domain.exceptions.LocalStorageNotRemovedException
import com.filesfm.android.domain.exceptions.MoveIntoDescendantException
import com.filesfm.android.domain.exceptions.NoConnectionWithServerException
import com.filesfm.android.domain.exceptions.NoNetworkConnectionException
import com.filesfm.android.domain.exceptions.OAuth2ErrorAccessDeniedException
import com.filesfm.android.domain.exceptions.OAuth2ErrorException
import com.filesfm.android.domain.exceptions.PartialCopyDoneException
import com.filesfm.android.domain.exceptions.PartialMoveDoneException
import com.filesfm.android.domain.exceptions.QuotaExceededException
import com.filesfm.android.domain.exceptions.RedirectToNonSecureException
import com.filesfm.android.domain.exceptions.SSLErrorException
import com.filesfm.android.domain.exceptions.ServerConnectionTimeoutException
import com.filesfm.android.domain.exceptions.ServerNotReachableException
import com.filesfm.android.domain.exceptions.ServerResponseTimeoutException
import com.filesfm.android.domain.exceptions.ServiceUnavailableException
import com.filesfm.android.domain.exceptions.ShareForbiddenException
import com.filesfm.android.domain.exceptions.ShareNotFoundException
import com.filesfm.android.domain.exceptions.ShareWrongParameterException
import com.filesfm.android.domain.exceptions.SpecificForbiddenException
import com.filesfm.android.domain.exceptions.SpecificMethodNotAllowedException
import com.filesfm.android.domain.exceptions.SpecificServiceUnavailableException
import com.filesfm.android.domain.exceptions.SpecificUnsupportedMediaTypeException
import com.filesfm.android.domain.exceptions.SyncConflictException
import com.filesfm.android.domain.exceptions.UnauthorizedException
import com.filesfm.android.domain.exceptions.UnhandledHttpCodeException
import com.filesfm.android.domain.exceptions.UnknownErrorException
import com.filesfm.android.domain.exceptions.WrongServerResponseException
import com.filesfm.android.lib.common.network.CertificateCombinedException
import com.filesfm.android.lib.common.operations.RemoteOperationResult
import java.net.SocketTimeoutException

fun <T> executeRemoteOperation(operation: () -> RemoteOperationResult<T>): T {
    operation.invoke().also {
        return handleRemoteOperationResult(it)
    }
}

private fun <T> handleRemoteOperationResult(
    remoteOperationResult: RemoteOperationResult<T>
): T {
    if (remoteOperationResult.isSuccess) {
        return remoteOperationResult.data
    }

    when (remoteOperationResult.code) {
        RemoteOperationResult.ResultCode.WRONG_CONNECTION -> throw NoConnectionWithServerException()
        RemoteOperationResult.ResultCode.NO_NETWORK_CONNECTION -> throw NoNetworkConnectionException()
        RemoteOperationResult.ResultCode.TIMEOUT -> {
            if (remoteOperationResult.exception is SocketTimeoutException) throw ServerResponseTimeoutException()
            else throw ServerConnectionTimeoutException()
        }
        RemoteOperationResult.ResultCode.HOST_NOT_AVAILABLE -> throw ServerNotReachableException()
        RemoteOperationResult.ResultCode.SERVICE_UNAVAILABLE -> throw ServiceUnavailableException()
        RemoteOperationResult.ResultCode.SSL_RECOVERABLE_PEER_UNVERIFIED -> throw remoteOperationResult.exception as CertificateCombinedException
        RemoteOperationResult.ResultCode.BAD_OC_VERSION -> throw BadOcVersionException()
        RemoteOperationResult.ResultCode.INCORRECT_ADDRESS -> throw IncorrectAddressException()
        RemoteOperationResult.ResultCode.SSL_ERROR -> throw SSLErrorException()
        RemoteOperationResult.ResultCode.UNAUTHORIZED -> throw UnauthorizedException()
        RemoteOperationResult.ResultCode.INSTANCE_NOT_CONFIGURED -> throw InstanceNotConfiguredException()
        RemoteOperationResult.ResultCode.FILE_NOT_FOUND -> throw FileNotFoundException()
        RemoteOperationResult.ResultCode.OAUTH2_ERROR -> throw OAuth2ErrorException()
        RemoteOperationResult.ResultCode.OAUTH2_ERROR_ACCESS_DENIED -> throw OAuth2ErrorAccessDeniedException()
        RemoteOperationResult.ResultCode.ACCOUNT_NOT_NEW -> throw AccountNotNewException()
        RemoteOperationResult.ResultCode.ACCOUNT_NOT_THE_SAME -> throw AccountNotTheSameException()
        RemoteOperationResult.ResultCode.OK_REDIRECT_TO_NON_SECURE_CONNECTION -> throw RedirectToNonSecureException()
        RemoteOperationResult.ResultCode.UNHANDLED_HTTP_CODE -> throw UnhandledHttpCodeException()
        RemoteOperationResult.ResultCode.UNKNOWN_ERROR -> throw UnknownErrorException()
        RemoteOperationResult.ResultCode.CANCELLED -> throw CancelledException()
        RemoteOperationResult.ResultCode.INVALID_LOCAL_FILE_NAME -> throw InvalidLocalFileNameException()
        RemoteOperationResult.ResultCode.INVALID_OVERWRITE -> throw InvalidOverwriteException()
        RemoteOperationResult.ResultCode.CONFLICT -> throw ConflictException()
        RemoteOperationResult.ResultCode.SYNC_CONFLICT -> throw SyncConflictException()
        RemoteOperationResult.ResultCode.LOCAL_STORAGE_FULL -> throw LocalStorageFullException()
        RemoteOperationResult.ResultCode.LOCAL_STORAGE_NOT_MOVED -> throw LocalStorageNotMovedException()
        RemoteOperationResult.ResultCode.LOCAL_STORAGE_NOT_COPIED -> throw LocalStorageNotCopiedException()
        RemoteOperationResult.ResultCode.QUOTA_EXCEEDED -> throw QuotaExceededException()
        RemoteOperationResult.ResultCode.ACCOUNT_NOT_FOUND -> throw AccountNotFoundException()
        RemoteOperationResult.ResultCode.ACCOUNT_EXCEPTION -> throw AccountException()
        RemoteOperationResult.ResultCode.INVALID_CHARACTER_IN_NAME -> throw InvalidCharacterInNameException()
        RemoteOperationResult.ResultCode.LOCAL_STORAGE_NOT_REMOVED -> throw LocalStorageNotRemovedException()
        RemoteOperationResult.ResultCode.FORBIDDEN -> throw ForbiddenException()
        RemoteOperationResult.ResultCode.SPECIFIC_FORBIDDEN -> throw SpecificForbiddenException()
        RemoteOperationResult.ResultCode.INVALID_MOVE_INTO_DESCENDANT -> throw MoveIntoDescendantException()
        RemoteOperationResult.ResultCode.INVALID_COPY_INTO_DESCENDANT -> throw CopyIntoDescendantException()
        RemoteOperationResult.ResultCode.PARTIAL_MOVE_DONE -> throw PartialMoveDoneException()
        RemoteOperationResult.ResultCode.PARTIAL_COPY_DONE -> throw PartialCopyDoneException()
        RemoteOperationResult.ResultCode.SHARE_WRONG_PARAMETER -> throw ShareWrongParameterException()
        RemoteOperationResult.ResultCode.WRONG_SERVER_RESPONSE -> throw WrongServerResponseException()
        RemoteOperationResult.ResultCode.INVALID_CHARACTER_DETECT_IN_SERVER -> throw InvalidCharacterException()
        RemoteOperationResult.ResultCode.DELAYED_FOR_WIFI -> throw DelayedForWifiException()
        RemoteOperationResult.ResultCode.LOCAL_FILE_NOT_FOUND -> throw LocalFileNotFoundException()
        RemoteOperationResult.ResultCode.SPECIFIC_SERVICE_UNAVAILABLE -> throw SpecificServiceUnavailableException(remoteOperationResult.httpPhrase)
        RemoteOperationResult.ResultCode.SPECIFIC_UNSUPPORTED_MEDIA_TYPE -> throw SpecificUnsupportedMediaTypeException()
        RemoteOperationResult.ResultCode.SPECIFIC_METHOD_NOT_ALLOWED -> throw SpecificMethodNotAllowedException()
        RemoteOperationResult.ResultCode.SHARE_NOT_FOUND -> throw ShareNotFoundException(remoteOperationResult.httpPhrase)
        RemoteOperationResult.ResultCode.SHARE_FORBIDDEN -> throw ShareForbiddenException(remoteOperationResult.httpPhrase)
        else -> throw Exception()
    }
}
