module JsonPrimitive
  def to_json
    raise 'no se puede convertir a json por ser primitivo'
  end
end


class Object
  def to_jsonp_instance_variables
    self.instance_variables.map do |var|
      "\"#{var.to_s[1..-1]}\":#{self.instance_variable_get(var).to_jsonp}"
    end
  end

  def to_jsonp
    "{#{self.to_jsonp_instance_variables.join(',')}}"
  end

  def to_json
    to_jsonp
  end
end

class Array
  include JsonPrimitive
  def to_jsonp
    "[#{self.map{|it|it.to_jsonp}.join(',')}]"
  end
end

class Numeric
  include JsonPrimitive
  def to_jsonp
    self.to_s
  end
end

class String
  include JsonPrimitive
  def to_jsonp
    '"'+self+'"'
  end
end


