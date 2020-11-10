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

import com.filesfm.android.data.authentication.repository.OCAuthenticationRepository
import com.filesfm.android.data.capabilities.repository.OCCapabilityRepository
import com.filesfm.android.data.files.repository.OCFileRepository
import com.filesfm.android.data.server.repository.OCServerInfoRepository
import com.filesfm.android.data.sharing.sharees.repository.OCShareeRepository
import com.filesfm.android.data.sharing.shares.repository.OCShareRepository
import com.filesfm.android.data.user.repository.OCUserRepository
import com.filesfm.android.domain.authentication.AuthenticationRepository
import com.filesfm.android.domain.capabilities.CapabilityRepository
import com.filesfm.android.domain.files.FileRepository
import com.filesfm.android.domain.server.ServerInfoRepository
import com.filesfm.android.domain.sharing.sharees.ShareeRepository
import com.filesfm.android.domain.sharing.shares.ShareRepository
import com.filesfm.android.domain.user.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<AuthenticationRepository> { OCAuthenticationRepository(get(), get()) }
    factory<CapabilityRepository> { OCCapabilityRepository(get(), get()) }
    factory<FileRepository> { OCFileRepository(get()) }
    factory<ServerInfoRepository> { OCServerInfoRepository(get()) }
    factory<ShareeRepository> { OCShareeRepository(get()) }
    factory<ShareRepository> { OCShareRepository(get(), get()) }
    factory<UserRepository> { OCUserRepository(get(), get()) }
}
