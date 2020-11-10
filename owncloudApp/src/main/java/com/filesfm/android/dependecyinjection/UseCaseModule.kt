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

import com.filesfm.android.domain.authentication.usecases.GetBaseUrlUseCase
import com.filesfm.android.domain.authentication.usecases.LoginBasicAsyncUseCase
import com.filesfm.android.domain.authentication.usecases.LoginOAuthAsyncUseCase
import com.filesfm.android.domain.authentication.usecases.SupportsOAuth2UseCase
import com.filesfm.android.domain.capabilities.usecases.GetCapabilitiesAsLiveDataUseCase
import com.filesfm.android.domain.capabilities.usecases.GetStoredCapabilitiesUseCase
import com.filesfm.android.domain.capabilities.usecases.RefreshCapabilitiesFromServerAsyncUseCase
import com.filesfm.android.domain.server.usecases.GetServerInfoAsyncUseCase
import com.filesfm.android.domain.sharing.sharees.GetShareesAsyncUseCase
import com.filesfm.android.domain.sharing.shares.usecases.CreatePrivateShareAsyncUseCase
import com.filesfm.android.domain.sharing.shares.usecases.CreatePublicShareAsyncUseCase
import com.filesfm.android.domain.sharing.shares.usecases.DeleteShareAsyncUseCase
import com.filesfm.android.domain.sharing.shares.usecases.EditPrivateShareAsyncUseCase
import com.filesfm.android.domain.sharing.shares.usecases.EditPublicShareAsyncUseCase
import com.filesfm.android.domain.sharing.shares.usecases.GetShareAsLiveDataUseCase
import com.filesfm.android.domain.sharing.shares.usecases.GetSharesAsLiveDataUseCase
import com.filesfm.android.domain.sharing.shares.usecases.RefreshSharesFromServerAsyncUseCase
import com.filesfm.android.domain.user.usecases.GetStoredQuotaUseCase
import com.filesfm.android.domain.user.usecases.GetUserAvatarAsyncUseCase
import com.filesfm.android.domain.user.usecases.GetUserInfoAsyncUseCase
import com.filesfm.android.domain.user.usecases.RefreshUserQuotaFromServerAsyncUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // Authentication
    factory { LoginBasicAsyncUseCase(get()) }
    factory { LoginOAuthAsyncUseCase(get()) }
    factory { SupportsOAuth2UseCase(get()) }
    factory { GetBaseUrlUseCase(get()) }

    // Capabilities
    factory { GetCapabilitiesAsLiveDataUseCase(get()) }
    factory { GetStoredCapabilitiesUseCase(get()) }
    factory { RefreshCapabilitiesFromServerAsyncUseCase(get()) }

    // Sharing
    factory { GetShareesAsyncUseCase(get()) }
    factory { GetSharesAsLiveDataUseCase(get()) }
    factory { GetShareAsLiveDataUseCase(get()) }
    factory { RefreshSharesFromServerAsyncUseCase(get()) }
    factory { CreatePrivateShareAsyncUseCase(get()) }
    factory { EditPrivateShareAsyncUseCase(get()) }
    factory { CreatePublicShareAsyncUseCase(get()) }
    factory { EditPublicShareAsyncUseCase(get()) }
    factory { DeleteShareAsyncUseCase(get()) }

    // User
    factory { GetStoredQuotaUseCase(get()) }
    factory { GetUserInfoAsyncUseCase(get()) }
    factory { RefreshUserQuotaFromServerAsyncUseCase(get()) }
    factory { GetUserAvatarAsyncUseCase(get()) }

    // Server
    factory { GetServerInfoAsyncUseCase(get()) }
}
