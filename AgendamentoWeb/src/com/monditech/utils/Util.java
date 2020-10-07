package com.monditech.utils;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.google.gson.Gson;

public class Util {

	public static List<Object> ResultSetToArrayList(ResultSet rs) throws SQLException {
		System.out.println("BBB-111");
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		System.out.println("BBB-222");
		ArrayList<Object> list = new ArrayList<Object>();
		System.out.println("BBB-333");
		while (rs.next()) {
			System.out.println("BBB-444");
			HashMap<Object, Object> row = new HashMap<Object, Object>(columns);
			System.out.println("BBB-555");
			for (int i = 1; i <= columns; ++i) {
				System.out.println("BBB-666");
				row.put(md.getColumnLabel(i), rs.getObject(i));
				System.out.println("BBB-777");
			}
			System.out.println("BBB-888");
			list.add(row);
			System.out.println("BBB-999");
		}
		System.out.println("BBB-000");
		return list;

	}
	
	public static String DateToString(Date date) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);

	}
	
	public static String DateToString(Date date, String format) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
		
	}

	public InputStream DocumentToInputstream(MultipartFormDataInput fileInput) throws Exception {

		Map<String, List<InputPart>> uploadForm = fileInput.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("documento");

		/*
		 * String fileName = "";
		 * 
		 * Parametros parametros = new Parametros();
		 * 
		 * URL url = new URL(parametros.ecmDocumentService + "?wsdl");
		 * 
		 * ECMDocumentServiceService DMEngineServiceService = new
		 * ECMDocumentServiceService(url); DocumentService documentEngineService =
		 * DMEngineServiceService.getDocumentServicePort();
		 * 
		 * Attachment attachment = new Attachment(); AttachmentArray attachmentArray =
		 * new AttachmentArray();
		 * 
		 */

		InputPart inputPart = inputParts.get(0);

//		MultivaluedMap<String, String> header = inputPart.getHeaders();
//		fileName = GetFileName(header);

//		Util util = new Util();

//		fileName = util.RetirarAcentos(fileName);

//		fileName = util.RetirarCaracterEspecial(fileName);

		// convert the uploaded file to inputstream
		InputStream inputStream = inputPart.getBody(InputStream.class, null);

		/*
		 * byte [] bytes = IOUtils.toByteArray(inputStream);
		 * 
		 * attachment.setFileName(fileName); attachment.setFileSize(bytes.length);
		 * //tamanho do arquivo em bytes attachment.setAttach(false); // se é um anexo
		 * ou o documento publicado (true = anexo/ false = documento)
		 * attachment.setEditing(false); // se está em edição ou não
		 * attachment.setFullPatch(fileName); //Caminho relativo do arquivo (Fluig
		 * verifica a pasta de upload do usuário que está sendo usado para realizar a
		 * publicação) attachment.setPrincipal(true); // Se é o arquivo principal
		 * attachment.setFilecontent(bytes); attachmentArray.getItem().add(attachment);
		 * 
		 * System.out.println(uploadForm.get("parentID").get(0).getBodyAsString());
		 * WebServiceMessageArray wsa =
		 * documentEngineService.createSimpleDocument(parametros.loginFluig,
		 * parametros.senhaFluig, 1,
		 * Integer.parseInt(uploadForm.get("parentID").get(0).getBodyAsString()),
		 * parametros.loginFluig, fileName, attachmentArray);
		 */

		return inputStream;
	}

	

	@SuppressWarnings("deprecation")
	public Calendar ConvertDateToCalendar(Date date) {
		
		Calendar retorno = Calendar.getInstance();
		retorno.set(date.getYear(), date.getMonth(), date.getDay(), date.getHours(), date.getMinutes(), date.getSeconds());
		
		return retorno;
		
	}

	@SuppressWarnings("deprecation")
	public String AlteraDiasUteis(int quantidadeDiasUteis) throws Exception {

		Date dataAtual = new Date();
	
		dataAtual.setHours(00);
		dataAtual.setMinutes(00);
		dataAtual.setSeconds(00);

		int dias;

		if (quantidadeDiasUteis > 0) {

			dias = 1;

		} else {

			quantidadeDiasUteis = quantidadeDiasUteis * -1;
			dias = -1;

		}

		for (int i = 0; i < quantidadeDiasUteis; i++) {

			dataAtual.setDate(dataAtual.getDate() + dias);

			if (dataAtual.getDay() == 0 || dataAtual.getDay() == 6) {

				quantidadeDiasUteis++;

			}

		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String dataRetorno = sdf.format(dataAtual);

		// O retorno desta função é uma String, porém ela é convertida para Date no
		// Javascript.

		return dataRetorno;

	}

	public String GetParametro(String parametro) throws Exception {
		
		SQL sql = new SQL();
		
		try {
			
			String query = "select valor, criptografado from mon_parametros where campo = ?";
			
			sql.PrepararQuery(query);
			sql.SetParametro(parametro);
	
			ResultSet resultSet = sql.Select();
			
			try {
				
				if (resultSet.next()) {
					
					if (resultSet.getBoolean("criptografado") == true) {
						
						return AES.decrypt(resultSet.getString("valor"));
						
					}
					else {
						
						return resultSet.getString("valor");
						
					}
	
				}
				else {
					
					return null;
					
				}
				
			}
			finally {
				
				resultSet.close();
				
			}
			
		}
		finally {
			
			sql.Close();
			
		}
		
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> GetUserData() throws Exception {						
		
		System.out.println("GETDATA");

		
		Util util = new Util();
		System.out.println(util.GetParametro("urlBlueEzAPI"));
		
		String json = "{\"user\": \"" + util.GetParametro("loginBlueEz") + "\", \"pass\": \"" + util.GetParametro("senhaBlueEz") + "\"}";
		String urlBlueEz = util.GetParametro("urlBlueEzAPI") + "/login";
		String jsonRetorno = AcessoHttp.sendPost(urlBlueEz, json, null, null);		

		Gson gson = new Gson(); 
		Map<String,Object> map = new HashMap<String,Object>();
		map = (Map<String,Object>) gson.fromJson(jsonRetorno, map.getClass());
		
		Map<String, Object> user = (Map<String, Object>) map.get("usuario");		
				
		String[] usuario = user.get("id").toString().split("[.]");		
		
		map.put("userId", usuario[0]);		
		
		return map;
		
	}
	
}