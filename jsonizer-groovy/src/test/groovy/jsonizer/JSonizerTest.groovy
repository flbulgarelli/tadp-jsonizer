package jsonizer

import static org.junit.Assert.*

import org.junit.Test

class JSonizerTest {

  static {
    JSonizer.toString()
  }


  /*
   * Las formas de convertir
   */
  def stringToJsonp(String string) {
      string.toJsonp()
  }

  def numberToJsonp(Number aNumber) {
      aNumber.toJsonp()
  }

  def collectionToJsonp(Collection aCollection) {
      aCollection.toJsonp()
  }

  def objectToJsonp(Object anObject) {
      anObject.toJsonp()
  }


  /*
   * Los tests
   */
  @Test
  void "jsonp de string es el string original con comillas"() {
    assert stringToJsonp("hola") == '"hola"'
  }

  @Test
  void "jsonp de string vacío es un string vacío"() {
    assert stringToJsonp("") == '""'
  }

  @Test
  void "jsonp del entero es el numero toString"()  {
    assert numberToJsonp(1) == "1"
  }

  @Test
  void "jsonp del cero es cero"()  {
    assert numberToJsonp(0) == "0"
  }


  @Test
  void "jsonp de un numero negativo mantiene el signo"()  {
    assert numberToJsonp(-34.5) == "-34.5"
  }


  @Test
  void "jsonp del double es el numero toString"()  {
    assert numberToJsonp(1.1) == "1.1"
  }


  @Test
  void "jsonp de la coleccion es el json de cada uno de los elementos concatenado"()  {
    assert collectionToJsonp([1, 2, "hola", [] ]) == '[1,2,"hola",[]]'
  }


  @Test
  void "jsonp de un objeto es el jsonp de cada uno de sus atributos con una clave"()  {
    assert objectToJsonp(new Dummy(y: [], x: 3)) == '{"x":3,"y":[]}'
  }

  @Test(expected = Exception)
  void "json de un objeto primitivo no esta definido"()  {
    collectionToJson([])
  }

  @Test
  void "json de un objeto no primitivo es jsonp"()  {
    def o = new Dummy(y: 5)
    assert objectToJsonp(o) == o.toJson()
  }


  @Test
  void "json de un objeto compuesto"()  {
    def d = new Dummy(y: 5, x: 3)
    def o = new CompoundObject(meaningOfLifeUniverseAndEverything:42, magic: ["abra", "ca", "dabra"], dummy: d)
    assert objectToJsonp(o) == '{"dummy":{"x":3,"y":5},"magic":["abra","ca","dabra"],"meaningOfLifeUniverseAndEverything":42}'
  }


  @Test
  void "json de una coleccion de objeto"()  {
    def d = new Dummy(y: 5, x: 3)
    def o = new CompoundObject(meaningOfLifeUniverseAndEverything:42)
    def d2 = new Dummy(x:1)
    assert objectToJsonp([d,o,d2]) == '[{"x":3,"y":5},{"meaningOfLifeUniverseAndEverything":42},{"x":1}]'
  }

  @Test
  void "json de una lista de listas"()  {
    assert objectToJsonp([[3, 5], ["hola"], [7,7,7], ["mundo"]]) == '[[3,5],["hola"],[7,7,7],["mundo"]]'
  }

}

class Dummy {
  def x
  def y
}

class CompoundObject {
    def dummy
    def magic
    def meaningOfLifeUniverseAndEverything
}