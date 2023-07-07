package ar.edu.unlam.organizador.data.di

import android.content.Context
import ar.edu.unlam.organizador.data.firebase.GrupoFirebaseRepo
import ar.edu.unlam.organizador.data.firebase.TareaFirebaseRepo
import ar.edu.unlam.organizador.data.firebase.UsuarioFirebaseRepo
import ar.edu.unlam.organizador.data.device.UsuarioLocalStorageRepo
import ar.edu.unlam.organizador.data.repositorios.GrupoRepositorio
import ar.edu.unlam.organizador.data.repositorios.TareaRepositorio
import ar.edu.unlam.organizador.data.repositorios.UsuarioLocalRepositorio
import ar.edu.unlam.organizador.data.repositorios.UsuarioRepositorio
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindGrupoRepo(grupoRepoImpl: GrupoFirebaseRepo): GrupoRepositorio

    @Binds
    abstract fun tareaRepo(tareaRepo: TareaFirebaseRepo): TareaRepositorio


    @Binds
    abstract fun usuarioRepo(usuarioRepo: UsuarioFirebaseRepo): UsuarioRepositorio
}