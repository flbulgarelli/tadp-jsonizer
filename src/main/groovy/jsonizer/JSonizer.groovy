package jsonizer

class JSonizer {
  
  static {
    Number.metaClass {
      toJsonp = { delegate.toString() }
    }

    Collection.metaClass {
      toJsonp = {
        def joinedElements =  delegate.collect{it.toJsonp()}.join(", ")
        "[${joinedElements}]"
      }
    }
    
    Object.metaClass {
      toJsonp = {
        "{" +
            delegate.
            properties.
            sort().
            findAll { name, value -> name != "class" && value != null }.
            collect { name, value -> "\"${name}\": ${value.toJsonp()}"   }.
            join(", ") +
         "}"
      }
      
      toJson = { delegate.toJsonp() }
      
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
