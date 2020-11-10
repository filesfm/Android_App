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

package com.filesfm.android.domain.authentication.usecases

import com.filesfm.android.domain.BaseUseCaseWithResult
import com.filesfm.android.domain.authentication.AuthenticationRepository

class GetBaseUrlUseCase(
    private val authenticationRepository: AuthenticationRepository
) : BaseUseCaseWithResult<String, GetBaseUrlUseCase.Params>() {

    override fun run(params: Params): String {
        require(params.accountName.isNotEmpty()) { "Invalid account name" }
        return authenticationRepository.getBaseUrl(params.accountName)
    }

    data class Params(
        val accountName: String
    )
}
