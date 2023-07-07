package ar.edu.unlam.organizador.data.device

import android.content.Context
import ar.edu.unlam.organizador.data.repositorios.UsuarioLocalRepositorio
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DeviceModule {

    @Provides
    @Singleton
    fun provideUsuarioLocalRepo(@ApplicationContext ctx: Context): UsuarioLocalRepositorio {
        return UsuarioLocalStorageRepo(ctx)
    }

}