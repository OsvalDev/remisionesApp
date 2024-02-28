package com.example.remissionapp.data.network.model

data class paymentBody(
    val cantidad: Int,
    val img: String,
    val numCompra: String,
    val numRemission: String,
    val pagoPersona: String,
    val responsable: Int,
    val imgEntrega: String,
    val imgIne: String,
    val calidadVal: Boolean,
    val cantidadVal: Boolean
)