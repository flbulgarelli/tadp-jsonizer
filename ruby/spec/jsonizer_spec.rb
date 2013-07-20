require 'rspec'
require_relative '../src/jsonizer'

describe 'jsonizer' do

  class Dummy
    def initialize(x, y)
      @x = x
      @y = y
    end
  end

  it 'jsonp de string es el string original con comillas' do
    'hola'.to_jsonp.should == '"hola"'
  end

  it 'jsonp del entero es el numero to_s' do
    1.to_jsonp.should == '1'
  end

  it 'jsonp del flotante es el numero' do
    1.1.to_jsonp.should == '1.1'
  end

  it 'jsonp de la coleccion es el json de cada uno de los elementos concatenado' do
    [1, 2, 'hola', []].to_jsonp.should == '[1,2,"hola",[]]'
  end

  it 'jsonp de un objeto es el jsonp de cada uno de sus atributos con una clave' do
    Dummy.new(3,[]).to_jsonp.should == '{"x":3,"y":[]}'
  end

  it 'json de un objeto primitivo no esta definido' do
    expect { [].to_json }.to raise_error
  end

  it 'json de un objeto no primitivo es jsonp' do
   o = Dummy.new(nil, 5)
   o.to_json.should == o.to_jsonp
  end


end