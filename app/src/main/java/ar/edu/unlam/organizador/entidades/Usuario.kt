package com.example.organizadordetareas.entidades

class Usuario (
    var nickname: String= "",
    var password:String = "",
    var codigoCuenta: Int = 0,
    var nombre: String = "",
    var apellido: String = ""
) {
    fun comprobarContraseniaAlCrear(contrasenia: String): Boolean {
        var contieneMayuscula = false
        var contieneMinuscula = false
        var contieneNumero = false
        var contieneCaracterEspecial = false

        val contraseniaCharArray = contrasenia.toCharArray()
        for(i in contraseniaCharArray) {
            if(i.isUpperCase()) contieneMayuscula = true
            else if (i.isLowerCase()) contieneMinuscula = true
            else if(i.isDigit()) contieneNumero = true
            else if(!i.isLetterOrDigit()) contieneCaracterEspecial = true
        }

        return if(contieneMayuscula && contieneMinuscula && contieneNumero && contieneCaracterEspecial) {
            this.password = contrasenia
            true
        } else {
            false
        }
    }
}