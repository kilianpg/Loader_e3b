package parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lowagie.text.DocumentException;

import executer.ActionFacade;
import executer.ActionFacadeClass;
import model.Agent;
import model.Localizacion;
import model.Operario;
import model.util.ModelException;
import reportwriter.ReportWriter;


public class RList implements ReadList {
	private ActionFacade aF = new ActionFacadeClass();
	private ArrayList<List<XSSFCell>> allUsers;
	private HashMap<String, String> map;


//	@Value("${csv.filepathname:tipo_agentes.csv}") 
	private static String csv_filepathname ="src/main/resources/agentTypes.csv";
	
	/**
	 * Lee el fichero excel de la ruta pasada por parametro Si el fichero no esta en
	 * formato excel, detiene la lectura y escribe en el log la causa del error. Va
	 * leyendo linea por linea(hay un usuario en cada linea): Para cada linea crea
	 * un objeto Agent y se lo pasa al metodo cargarDatos del AtionFacade. Si existe
	 * algun fallo de FORMATO se ignora esa linea y se pasa a la siguiente, ademas
	 * de escribir dicho error en el log.
	 * 
	 * @param path
	 *            ruta del fichero
	 * 
	 * @exception FileNotFoundException
	 *                No se encuentra el fichero excel
	 * @throws DocumentException
	 */
	@Override
	public void load(String path) throws FileNotFoundException, DocumentException {
		loadFicheroMaestro(csv_filepathname);
		InputStream excelFile = null;
		XSSFWorkbook excel = null;
		allUsers = new ArrayList<List<XSSFCell>>();
		int i = 0;
		try {
			excelFile = new FileInputStream(path);
			excel = new XSSFWorkbook(excelFile);
			XSSFSheet sheet = excel.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;
			List<XSSFCell> user;
			Iterator<Row> rows = sheet.rowIterator();

			while (rows.hasNext()) {
				user = new ArrayList<XSSFCell>();
				row = (XSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();
				if (i > 0) {
					while (cells.hasNext()) {
						cell = (XSSFCell) cells.next();
						user.add(cell);
					}
					allUsers.add(user);
					crearUsuarios(user);
				}
				i++;
			}
		} catch (FileNotFoundException ex) {
			throw ex;
		} catch (IOException ioe) {
			System.err.println("Problema con la lectura del excel en la linea " + i);
			ReportWriter.getInstance().getWriteReport().log(Level.WARNING,
					"Problema con la lectura del excel en la linea " + i);
		} catch (ModelException e) {
			System.err.println("Se ha intentado crear un sensor sin localización o con valor nulo");
		} finally {
			if (excelFile != null) {
				try {
					excelFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (excel != null) {
				try {
					excel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ActionFacade getaF() {
		return aF;
	}

	public void setaF(ActionFacade aF) {
		this.aF = aF;
	}

	private void loadFicheroMaestro(String path) {

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(csv_filepathname));
			String line = null;
			map = new HashMap<String, String>();

			while ((line = br.readLine()) != null) {
				System.out.println("line: " + line);
				String str[] = line.split(";");
				map.put(str[1], str[0]);
			}

			System.out.println(map);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void crearUsuarios(List<XSSFCell> list)
			throws FileNotFoundException, DocumentException, IOException, NumberFormatException, ModelException {

		DataFormatter formatter = new DataFormatter();
		String[] local = list.get(1).getStringCellValue().split(" ");
//		System.out.println(formatter.formatCellValue(list.get(4)));
		
		if(!(list.get(4).getStringCellValue().equals("4"))) { //persona,entidad,sensor

		Agent user = new Agent(list.get(0).getStringCellValue(),
				new Localizacion(Double.parseDouble(local[0]), Double.parseDouble(local[1])).toString(),
				list.get(2).getStringCellValue(), formatter.formatCellValue(list.get(3)),
				map.get(formatter.formatCellValue(list.get(4))));
		
		InsertR insert = new InsertR();
		insert.save(user);
		}
		else //operario
		{
			
			Operario oper= new Operario(list.get(2).getStringCellValue(),list.get(3).getStringCellValue());
			
			InsertR insert = new InsertR();
			insert.save(oper);
			
		}
		// getaF().saveData(user);
	}

	public ArrayList<List<XSSFCell>> getAllUsers() {
		return allUsers;
	}

	public void loadSimulacion(String path) throws FileNotFoundException {
		
		InputStream excelFile = null;
		@SuppressWarnings("unused")
		XSSFWorkbook excel = null;
		allUsers = new ArrayList<List<XSSFCell>>();
		int i = 0;
		try {
			excelFile = new FileInputStream(path);
			excel = new XSSFWorkbook(excelFile);
			
			}
			catch (FileNotFoundException ex) {
			throw ex;
		} catch (IOException ioe) {
			System.err.println("Problema con la lectura del excel en la linea " + i);
			ReportWriter.getInstance().getWriteReport().log(Level.WARNING,
					"Problema con la lectura del excel en la linea " + i);
		
	}

}
}
