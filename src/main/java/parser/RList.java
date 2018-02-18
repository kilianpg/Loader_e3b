package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import model.util.ModelException;
import reportwriter.ReportWriter;


public class RList implements ReadList {
	private ActionFacade aF = new ActionFacadeClass();
	private ArrayList<List<XSSFCell>> allUsers;

	/**
	 * Lee el fichero excel de la ruta pasada por parametro Si el fichero no
	 * esta en formato excel, detiene la lectura y escribe en el log la causa
	 * del error. Va leyendo linea por linea(hay un usuario en cada linea): Para
	 * cada linea crea un objeto Agent y se lo pasa al metodo cargarDatos del
	 * AtionFacade. Si existe algun fallo de FORMATO se ignora esa linea y se
	 * pasa a la siguiente, ademas de escribir dicho error en el log.
	 * 
	 * @param path
	 *            ruta del fichero
	 * 
	 *  @exception FileNotFoundException No se encuentra el fichero excel
	 * @throws DocumentException 
	 */
	@Override
	public void load(String path) throws FileNotFoundException, DocumentException{
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
		} catch(FileNotFoundException ex){
			throw ex;
		}
		catch (IOException ioe) {
			System.err.println("Problema con la lectura del excel en la linea " + i);
			ReportWriter.getInstance().getWriteReport().log(Level.WARNING, "Problema con la lectura del excel en la linea " + i);
		} catch (ModelException e) {
			System.err.println("Se ha intentado crear un sensor sin localización o con valor nulo");
		}finally {
			if (excelFile != null){
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

	private void crearUsuarios(List<XSSFCell> list) throws FileNotFoundException, DocumentException, IOException, NumberFormatException, ModelException {
		DataFormatter formatter = new DataFormatter();
		String[] local = list.get(1).getStringCellValue().split(" ");
		Agent user = new Agent(list.get(0).getStringCellValue(),new Localizacion(Double.parseDouble(local[0]),
				Double.parseDouble(local[1])), list.get(2).getStringCellValue(), 
		formatter.formatCellValue(list.get(3)),formatter.formatCellValue(list.get(4)));
		InsertR insert = new InsertR();
		insert.save(user);
		//getaF().saveData(user);
	}
	
	public ArrayList<List<XSSFCell>> getAllUsers(){
		return allUsers;
	}

}
