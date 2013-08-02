package jsonizer

class JSonizer {

  static {
    Number.metaClass {
      toJsonp = { delegate.toString() }
    }

    Collection.metaClass {
      toJsonp = {
        def joinedElements =  delegate.collect{it.toJsonp()}.join(",")
        "[${joinedElements}]"
      }
    }

    Object.metaClass {
      toJsonp = { formatter = "minified" ->
        delegate."formatJson${formatter.capitalize()}"(
          delegate.
          properties.
          sort().
          findAll { name, value -> name != "class" && value != null }.
          collect { name, value -> [ name ,  value.toJsonp(formatter)] }
        )
      }
      toJson = {
        formatter = "minified" -> delegate.toJsonp(formatter)
      }
      formatJsonBeautified  = { attributes ->
        "{\n" + attributes.collect { name, value -> "\"${name}\" : ${value}" }.join(",\n") + " }"
      }
      formatJsonMinified = { attributes ->
        "{" + attributes.collect { name, value -> "\"${name}\":${value}" }.join(",") + "}"
      }

    }

    String.mixin(Terminal)
    Number.mixin(Primitive)
    Collection.mixin(Primitive)
  }
}

class Primitive {
  def toJson() {
    throw new Exception("no se puede convertir por ser primitivo")
  }
}

class Terminal extends Primitive {
  def toJsonp() {
    "\"${toString()}\""
  }
}
