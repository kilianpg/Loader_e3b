package modeltest;

import static org.junit.Assert.*;

import model.Agent;
import model.Incidence;
import model.Localizacion;
import model.Operario;
import model.util.ModelException;

import org.junit.Test;

public class AgentTest {

	@Test
	public void testEquals() throws ModelException {		
		//coordenadas opcionales 
		Agent agenteCiudadano1 = new Agent("Dani",null,"dani35@gmail.com","dani123","Ciudadano");
		Agent agenteCiudadano1_1 = new Agent("Dani",null,"dani1456@gmail.com","dani123","Ciudadano");
		Agent agenteCiudadano2 = new Agent("Julio",null,"julio1235@gmail.com","julio125","Ciudadano");
		//
		Agent agenteEntidad1 = new Agent("Entidad 1",null,"entidad123@gmail.com","entidad123","Entidad");
		Agent agenteEntidad1_1 = new Agent("Entidad 1.1",null,"entidad123567@gmail.com","entidad123","Entidad");
		Agent agenteEntidad2 = new Agent("Entidad 2",null,"entidad123@gmail.com","entidad567","Entidad");
		
		//
		
		Agent agentSensor1 = new Agent("Sensor 1",new Localizacion(43,-6).toString(),"sensor@gmail.com","sensor123","Sensor");
		Agent agentSensor1_1= new Agent("Sensor 1.1",new Localizacion(43,-6).toString(),"sensor1.1@gmail.com","sensor123","Sensor");
		Agent agentSensor2= new Agent("Sensor 2",new Localizacion(43,-6).toString(),"sensor2@gmail.com","sensor456","Sensor");
		
		//
		Operario oper1 = new Operario("oper1@gmail.com","123456");
		Operario oper2 = new Operario("oper2@gmail.com","123456");
		Operario oper3 = new Operario("oper3@gmail.com","123456");
		
		
		assertNotNull(agenteCiudadano1);
		assertNotNull(agenteCiudadano1_1);
		assertNotNull(agenteCiudadano2);
		assertNotNull(agenteEntidad1);
		assertNotNull(agenteEntidad1_1);
		assertNotNull(agenteEntidad2);
		assertNotNull(agentSensor1);
		assertNotNull(agentSensor1_1);
		assertNotNull(agentSensor2);
		assertNotNull(oper1);
		assertNotNull(oper2);
		assertNotNull(oper3);

		assertEquals(true, agenteCiudadano1.equals(agenteCiudadano1_1));
		assertEquals(true, agenteEntidad1.equals(agenteEntidad1_1));
		assertEquals(true, agentSensor1.equals(agentSensor1_1));
		
		
		assertEquals(false, agenteCiudadano1.equals(agenteCiudadano2));
		assertEquals(false, agenteEntidad1.equals(agenteEntidad2));
		assertEquals(false, agentSensor1.equals(agentSensor2));
	}

	@Test
	public void testHashCode() throws ModelException {
	
		Agent agenteCiudadano1 = new Agent("Dani",null,"dani35@gmail.com","dani123","Ciudadano");
		Agent agenteCiudadano1_1 = new Agent("Dani",null,"dani1456@gmail.com","dani123","Ciudadano");
		Agent agenteCiudadano2 = new Agent("Julio",null,"julio1235@gmail.com","julio125","Ciudadano");
		
		Agent agenteEntidad1 = new Agent("Entidad 1",null,"entidad123@gmail.com","entidad123","Entidad");
		Agent agenteEntidad1_1 = new Agent("Entidad 1.1",null,"entidad123567@gmail.com","entidad123","Entidad");
		Agent agenteEntidad2 = new Agent("Entidad 2",null,"entidad123@gmail.com","entidad567","Entidad");

		assertEquals(agenteCiudadano1.hashCode(), agenteCiudadano1_1.hashCode());
		assertNotEquals(agenteCiudadano1.hashCode(), agenteCiudadano2.hashCode());
		
		assertEquals(agenteEntidad1.hashCode(), agenteEntidad1_1.hashCode());
		assertNotEquals(agenteEntidad1.hashCode(), agenteEntidad2.hashCode());
		

	}

	@Test(expected = ModelException.class)
	public void testConstructor() throws ModelException {
		
		Agent agenteCiudadano1 = new Agent("Dani",null,"dani35@gmail.com","dani123","Ciudadano");

		assertNotNull(agenteCiudadano1);
		assertEquals("Dani", agenteCiudadano1.getNombre());
		assertEquals("dani35@gmail.com", agenteCiudadano1.getEmail());
		assertEquals("dani123", agenteCiudadano1.getIdentificador());
		assertEquals("Ciudadano", agenteCiudadano1.getTipo());
		assertNull(agenteCiudadano1.getLocalizacion());
		
		Agent agentSensor1 = new Agent("Sensor 1",new Localizacion(43,-6).toString(),"sensor@gmail.com","sensor123","Sensor");
		
		assertNotNull(agentSensor1);
		assertEquals("Sensor 1", agentSensor1.getNombre());
		assertEquals("sensor@gmail.com", agentSensor1.getEmail());
		assertEquals("sensor123", agentSensor1.getIdentificador());
		assertEquals("Sensor", agentSensor1.getTipo());
		assertNotNull(agentSensor1.getLocalizacion());
		System.out.println(new Localizacion(43,-6).toString());
		assertTrue(new Localizacion(43,-6).toString().equals(agentSensor1.getLocalizacion()));
		
		
		
		new Agent("Sensor 1",null,"sensor@gmail.com","sensor123","Sensor");
		
		Operario oper1 = new Operario("oper1@gmail.com","123456");
		
		assertNotNull(oper1);
		assertEquals("oper1@gmail.com", oper1.getEmail());
		assertEquals("123456", oper1.getPassword());		

	}
	
	
	@Test
	public void testSettersGetters() throws ModelException {
	
		Agent agenteCiudadano1 = new Agent("Dani",null,"dani35@gmail.com","dani123","Ciudadano");	

		Localizacion localizacion = new Localizacion(1, 1);
		agenteCiudadano1.setNombre("Pepe");
		assertEquals("Pepe",agenteCiudadano1.getNombre());
		agenteCiudadano1.setTipo("Sensor");
		assertEquals("Sensor",agenteCiudadano1.getTipo());
		agenteCiudadano1.setLocalizacion(localizacion.toString());
		assertEquals(localizacion.toString(),agenteCiudadano1.getLocalizacion());
		agenteCiudadano1.setIdentificador("pepe123");
		assertEquals("pepe123",agenteCiudadano1.getIdentificador());
		agenteCiudadano1.setEmail("pepe@gmail.com");
		assertEquals("pepe@gmail.com",agenteCiudadano1.getEmail());
		agenteCiudadano1.toString();
	}
	
	@Test
	public void testEqualsFal() throws ModelException {		
		//coordenadas opcionales 
		Agent agenteCiudadano1 = new Agent("Dani",null,"dani35@gmail.com","dani123","Ciudadano");
		Agent agenteCiudadano1_1 = new Agent("Dani",null,"dani35@gmail.com",null,"Ciudadano");
				
		Incidence inc= new Incidence();
		
		
		assertNotNull(agenteCiudadano1);
		assertNotNull(agenteCiudadano1_1);
		
		assertFalse(agenteCiudadano1.equals(null));
		assertFalse(agenteCiudadano1.equals(inc));
		assertFalse(agenteCiudadano1.equals(agenteCiudadano1_1));
		
	}
}
