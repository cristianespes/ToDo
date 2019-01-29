package com.cristianespes.todo

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MockitoExampleTest {

    // Método Java
    //@Mock
    //lateinit var mutableList: MutableList<String>
    // Deberiamos inicializarlo al principio de cada test

    // Método Kotlin
    val mutableList: MutableList<String> = mock()

    // Función que se ejecuta antes de cada test unitario
    /*@Before
    fun setUp() {
        // Inicializar todas las variables que necesitemos

        // Lee todas las variables con anotación @Mock y hace un mock de ellos
        // MockitoAnnotations.initMocks(this) // Método Java
    }*/

    // Función que se ejecuta después de cada test unitario
    /*@After
    fun shutDown() {
        // Desprendernos de todas las variables que debamos
    }*/

    // Se ejecuta antes de toda la clase
    //@BeforeClass
    //fun setUpClass() { }

    // Se ejecuta después de toda la clase
    //@AfterClass
    //fun shutDownClass() { }


    @Test
    // Con mockito kotlin, y las comillas invertidas podemos poner nombres de funciones explicitos
    fun `Basic test to prove mockito`() {
        mutableList.add("test") // Simula que añade un valor

        // Test unitario
        //Assert.assertEquals(1, mutableList.size) // Junit nos dice qué ocurre
        // Este método dará error porque realmente no añade nada, mutableList es un mock: val mutableList: MutableList<String> = mock()
        // Si fuera val mutableList: MutableList<String> = mutableListOf(), el test unitario sería correcto.

        // mockito (normal)
        //verify(mutableList).add("test2")
        // dará error porque esperaba un "test", y no un "test2"

        verify(mutableList).add("test") // verifica que es correcto
    }

}