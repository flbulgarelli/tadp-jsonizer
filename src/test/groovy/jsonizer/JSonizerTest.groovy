package jsonizer

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

class JSonizerTest {

  static {
    JSonizer.toString()
  }

  @Test
  void "jsonp de string es el string original con comillas"() {
    assert "hola".toJsonp() == '"hola"'
  }

  @Test
  void "jsonp del entero es el numero toString"()  {
    assert 1.toJsonp() == "1"
  }

  @Test
  void "jsonp del double es el numero toString"()  {
    assert 1.1.toJsonp() == "1.1"
  }


  @Test
  void "jsonp de la coleccion es el json de cada uno de los elementos concatenado"()  {
    assert [1, 2, "hola", [] ].toJsonp() == '[1, 2, "hola", []]' 
  }


  @Test
  void "jsonp de un objeto es el jsonp de cada uno de sus atributos con una clave"()  {
    assert new Dummy(y: [], x: 3).toJsonp() == '{"x": 3, "y": []}'
  }
  
  @Test(expected = Exception)
  void "json de un objeto primitivo no esta definido"()  {
    [].toJson()
  }
  
  @Test
  void "json de un objeto no primitivo es jsonp"()  {
    def o = new Dummy(y: 5)
    assert o.toJsonp() == o.toJson()   
  }
  
}

class Dummy {
  def x
  def y
}