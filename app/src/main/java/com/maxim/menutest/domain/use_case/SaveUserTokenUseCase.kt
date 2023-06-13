package com.maxim.menutest.domain.use_case

import com.maxim.menutest.domain.repository.MenuRepository

class SaveUserTokenUseCase(
    private val repository: MenuRepository
) {

     operator fun invoke(token: String) {
        return repository.saveUserToken(token)
    }
}