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

package com.filesfm.android.dependecyinjection

import com.filesfm.android.R
import com.filesfm.android.authentication.AccountUtils
import com.filesfm.android.data.ClientManager
import com.filesfm.android.data.authentication.datasources.RemoteAuthenticationDataSource
import com.filesfm.android.data.authentication.datasources.implementation.OCRemoteAuthenticationDataSource
import com.filesfm.android.data.capabilities.datasources.RemoteCapabilitiesDataSource
import com.filesfm.android.data.capabilities.datasources.implementation.OCRemoteCapabilitiesDataSource
import com.filesfm.android.data.capabilities.datasources.mapper.RemoteCapabilityMapper
import com.filesfm.android.data.files.datasources.RemoteFileDataSource
import com.filesfm.android.data.files.datasources.implementation.OCRemoteFileDataSource
import com.filesfm.android.data.server.datasources.RemoteServerInfoDataSource
import com.filesfm.android.data.server.datasources.implementation.OCRemoteServerInfoDataSource
import com.filesfm.android.data.sharing.sharees.datasources.RemoteShareeDataSource
import com.filesfm.android.data.sharing.sharees.datasources.implementation.OCRemoteShareeDataSource
import com.filesfm.android.data.sharing.shares.datasources.RemoteShareDataSource
import com.filesfm.android.data.sharing.shares.datasources.implementation.OCRemoteShareDataSource
import com.filesfm.android.data.sharing.shares.datasources.mapper.RemoteShareMapper
import com.filesfm.android.data.user.datasources.RemoteUserDataSource
import com.filesfm.android.data.user.datasources.implementation.OCRemoteUserDataSource
import com.filesfm.android.data.user.datasources.mapper.RemoteUserAvatarMapper
import com.filesfm.android.data.user.datasources.mapper.RemoteUserInfoMapper
import com.filesfm.android.data.user.datasources.mapper.RemoteUserQuotaMapper
import com.filesfm.android.lib.common.OwnCloudAccount
import com.filesfm.android.lib.common.SingleSessionManager
import com.filesfm.android.lib.resources.files.services.FileService
import com.filesfm.android.lib.resources.files.services.implementation.OCFileService
import com.filesfm.android.lib.resources.shares.services.ShareService
import com.filesfm.android.lib.resources.shares.services.ShareeService
import com.filesfm.android.lib.resources.shares.services.implementation.OCShareService
import com.filesfm.android.lib.resources.shares.services.implementation.OCShareeService
import com.filesfm.android.lib.resources.status.services.CapabilityService
import com.filesfm.android.lib.resources.status.services.ServerInfoService
import com.filesfm.android.lib.resources.status.services.implementation.OCCapabilityService
import com.filesfm.android.lib.resources.status.services.implementation.OCServerInfoService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single { AccountUtils.getCurrentOwnCloudAccount(androidContext()) }
    single { OwnCloudAccount(get(), androidContext()) }
    single { SingleSessionManager.getDefaultSingleton().getClientFor(get(), androidContext()) }

    single { ClientManager(get(), get()) }

    single<CapabilityService> { OCCapabilityService(get()) }
    single<FileService> { OCFileService(get()) }
    single<ServerInfoService> { OCServerInfoService() }
    single<ShareService> { OCShareService(get()) }
    single<ShareeService> { OCShareeService(get()) }

    factory<RemoteAuthenticationDataSource> { OCRemoteAuthenticationDataSource(androidContext(), get()) }
    factory<RemoteCapabilitiesDataSource> { OCRemoteCapabilitiesDataSource(get(), get()) }
    factory<RemoteFileDataSource> { OCRemoteFileDataSource(get()) }
    factory<RemoteServerInfoDataSource> { OCRemoteServerInfoDataSource(get()) }
    factory<RemoteShareDataSource> { OCRemoteShareDataSource(get(), get()) }
    factory<RemoteShareeDataSource> { OCRemoteShareeDataSource(get()) }
    factory<RemoteUserDataSource> { OCRemoteUserDataSource(get(), get(), get(), get(), androidContext().resources.getDimension(
                R.dimen.file_avatar_size
            ).toInt()) }

    factory { RemoteCapabilityMapper() }
    factory { RemoteShareMapper() }
    factory { RemoteUserAvatarMapper() }
    factory { RemoteUserInfoMapper() }
    factory { RemoteUserQuotaMapper() }
}
