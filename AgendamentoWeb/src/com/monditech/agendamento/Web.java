package com.monditech.agendamento;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.ejb.LocalBean;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.monditech.agendamento.Retorno;
import com.monditech.dto.scheduling;
import com.monditech.utils.SQL;
import com.monditech.utils.Util;

@LocalBean
@Path("/")
public class Web {
	
	@GET
    @Path("/GetTeste")
	public Response GetTeste() throws IOException, Exception {

		return Retorno.Sucesso("Agendamento Web!");
				
	}
	
	@Produces("application/json")
	@GET
    @Path("/getScheduling")
	public Response GetScheduling() throws IOException, Exception {
		
		
		

		SQL sql = new SQL(null);
		
		try {
	
			String query = 	"select id, "
					+ "DATE_FORMAT(schedulingmanutence.dtStart, '%d/%m/%Y') AS dtStart,"
					+ "DATE_FORMAT(schedulingmanutence.dtEnd, '%d/%m/%Y') AS dtEnd, "
					+ "DATE_FORMAT(schedulingmanutence.dtStartPerid, '%H:%i:%s') AS dtStartPerid," 
					+ "DATE_FORMAT(schedulingmanutence.dtEndPerid, '%H:%i:%s') AS dtEndPerid,"
					+ "DATE_FORMAT(schedulingmanutence.dtStartPresent, '%H:%i:%s') as dtStartPresent," 
					+ "DATE_FORMAT(schedulingmanutence.dtEndPresent, '%H:%i:%s') as dtEndPresent," 
					+ "DATE_FORMAT(schedulingmanutence.dtStartDelay, '%H:%i:%s') as dtStartDelay," 
					+ "DATE_FORMAT(schedulingmanutence.dtEndDelay, '%H:%i:%s') as dtEndDelay," 
					+ "active " + 
							"from schedulingmanutence";
			
			sql.PrepararQuery(query);
		
			ResultSet resultSet = sql.Select();
			
			try {
		
				Gson gson = new Gson();
			
				return Retorno.Sucesso(gson.toJson(Util.ResultSetToArrayList(resultSet))) ;
				
			}
			finally {
			
				resultSet.close();
				
			}
			
		}
		finally {
			
			sql.Close();
			
		}
	}
	
	@Produces("application/json")
	@POST
    @Path("/getSchedule")
	public Response GetSchedule(Integer id) throws IOException, Exception {
		
		
		

		SQL sql = new SQL(null);
		
		try {
			System.out.println(id);
			String query = 	"select " 
							+ "DATE_FORMAT(schedulingmanutence.dtStart, '%d/%m/%Y') AS dtStart," 
							+ "DATE_FORMAT(schedulingmanutence.dtEnd, '%d/%m/%Y') AS dtEnd,"  
							+ "DATE_FORMAT(schedulingmanutence.dtStartPerid, '%H:%i:%s') AS dtStartPerid," 
							+ "DATE_FORMAT(schedulingmanutence.dtEndPerid, '%H:%i:%s') AS dtEndPerid,"
							+ "DATE_FORMAT(schedulingmanutence.dtStartPresent, '%H:%i:%s') as dtStartPresent," 
							+ "DATE_FORMAT(schedulingmanutence.dtEndPresent, '%H:%i:%s') as dtEndPresent," 
							+ "DATE_FORMAT(schedulingmanutence.dtStartDelay, '%H:%i:%s') as dtStartDelay," 
							+ "DATE_FORMAT(schedulingmanutence.dtEndDelay, '%H:%i:%s') as dtEndDelay,"
							+ "active " 
							+ "from schedulingmanutence "
							+ "where id = ?";
			
			sql.PrepararQuery(query);
			
			sql.SetParametro(id);
			
		
			ResultSet resultSet = sql.Select();
			
			try {
	
				Gson gson = new Gson();
		
				return Retorno.Sucesso(gson.toJson(Util.ResultSetToArrayList(resultSet))) ;
				
			}
			finally {
			
				resultSet.close();
				
			}
			
		}
		finally {
			
			sql.Close();
			
		}
	}
	
	@SuppressWarnings("finally")
	@Produces("application/json")
	@POST
    @Path("/postScheduling")
	public Response PostScheduling(scheduling scheduling) throws IOException, Exception {
		System.out.println("get start" + scheduling.getDtStart());
		System.out.println("get end" + scheduling.getDtEnd());
		String result = ""
				+ " { \"msg\": \"Resutado Salvo com Sucesso\" ,  "
				+ "}";

	
		
		SQL sql = new SQL(null);
		
		
		
		try {
			
			
			String query = 	"insert schedulingmanutence (dtStart, dtEnd, dtStartPerid, dtEndPerid, dtStartPresent, dtEndPresent, dtStartDelay, dtEndDelay, active) " + 
					"values (?,?,?,?,?,?,?,?,?)";

			sql.PrepararQuery(query);
			sql.SetParametro(scheduling.getDtStart());
			sql.SetParametro(scheduling.getDtEnd());
			sql.SetParametro(scheduling.getDtStartPerid());
			sql.SetParametro(scheduling.getDtEndPerid());
			sql.SetParametro(scheduling.getDtStartPresent());
			sql.SetParametro(scheduling.getDtEndPresent());
			sql.SetParametro(scheduling.getDtStartDelay());
			sql.SetParametro(scheduling.getDtEndDelay());
			sql.SetParametro(scheduling.getActive());
			
			sql.InsertUpdate();
			sql.Commit();

		}
		finally {
			
			sql.Close();
			return Retorno.Sucesso(result);
			
		}
		

	}
	
	@SuppressWarnings("finally")
	@Produces("application/json")
	@PUT
    @Path("/putScheduling")
	public Response PutScheduling(scheduling scheduling) throws IOException, Exception {
		String result = ""
				+ " { \"msg\": \"Resutado Salvo com Sucesso\" ,  "
				+ "}";
		
		SQL sql = new SQL(null);
		


		try {
			
			String query = 	"update schedulingmanutence " + 
					"set dtStart = ?, dtEnd = ?, dtStartPerid = ?, dtEndPerid = ?, dtStartPresent = ?, dtEndPresent = ?, dtStartDelay = ?, dtEndDelay = ?, active = ?" +
					" where id = ?";
			

			
			sql.PrepararQuery(query);
			
	
			sql.SetParametro(scheduling.getDtStart());
	
			sql.SetParametro(scheduling.getDtEnd());
			
			sql.SetParametro(scheduling.getDtStartPerid());
	
			sql.SetParametro(scheduling.getDtEndPerid());
		
			sql.SetParametro(scheduling.getDtStartPresent());
		
			sql.SetParametro(scheduling.getDtEndPresent());
	
			sql.SetParametro(scheduling.getDtStartDelay());
		
			sql.SetParametro(scheduling.getDtEndDelay());
	
			sql.SetParametro(scheduling.getActive());
	
			sql.SetParametro(scheduling.getId());
			
			
			sql.InsertUpdate();
			
			sql.Commit();

		}
		finally {
			
			sql.Close();
			return Retorno.Sucesso(result);
			
		}
		
		

	}
	
	@Produces("application/json")
	@POST
    @Path("/FiltersSheduling")
	public Response SchedulingFilters(scheduling scheduling) throws IOException, Exception { 
		String result = ""
				+ " { \"msg\": \"Resutado Salvo com Sucesso\" ,  "
				+ "}";
		

		SQL sql = new SQL(null);
		

		
		try {

			String query =  "select "
							+ "id,"
							+ "DATE_FORMAT(schedulingmanutence.dtStart, '%d/%m/%Y') AS dtStart, "
							+ "DATE_FORMAT(schedulingmanutence.dtEnd, '%d/%m/%Y') AS dtEnd, "
							+ "DATE_FORMAT(schedulingmanutence.dtStartPerid, '%H:%i:%s') AS dtStartPerid, " 
							+ "DATE_FORMAT(schedulingmanutence.dtEndPerid, '%H:%i:%s') AS dtEndPerid, "
							+ "DATE_FORMAT(schedulingmanutence.dtStartPresent, '%H:%i:%s') as dtStartPresent, " 
							+ "DATE_FORMAT(schedulingmanutence.dtEndPresent, '%H:%i:%s') as dtEndPresent, " 
							+ "DATE_FORMAT(schedulingmanutence.dtStartDelay, '%H:%i:%s') as dtStartDelay, " 
							+ "DATE_FORMAT(schedulingmanutence.dtEndDelay, '%H:%i:%s') as dtEndDelay, "
							+ "active " 
							+ "from schedulingmanutence " 
							+ "where 1=1"; 
			
			
		                    if (scheduling.getDtStart() != null) {
		                        query += " and dtStart >= ? ";
		
		                    }
		
		                    if (scheduling.getDtEnd() != null) {

		                        query += " and dtEnd <= ? ";
		                    }
							
					
							if (scheduling.getDtStartPerid() != null) {

								query += " and dtStartPerid >= ? ";
								
							}
						
							if (scheduling.getDtEndPerid() != null) {
	
								
			                    query += " and dtEndPerid <= ? ";
							}
							
							if (scheduling.getDtStartPresent() != null  ) {
								query += " and dtStartPresent >= ? ";
								
								
							}
							
							if (scheduling.getDtEndPresent() != null ) {
								
								query += " and dtEndPresent <= ? ";
								

							}
							
							if (scheduling.getDtStartDelay() != null) {
			
								
			                    query += " and dtStartDelay <= ? ";
								
							}
							
							if (scheduling.getDtEndDelay() != null) {
		
						
					
					
									query += " and dtEndDelay >= ? ";
									
				           
							}
							
							if (scheduling.getActive() != null) {
								query += " and active = ";
						
									query += "?";

								
								
							}

						
			sql.PrepararQuery(query);
			System.out.println(query);
			
			if (scheduling.getDtStart() != null) {
			
				sql.SetParametro(scheduling.getDtStart());
				sql.SetParametro(scheduling.getDtStart());
			}
			
			if (scheduling.getDtEnd() != null) {
			
				sql.SetParametro(scheduling.getDtEnd());
				sql.SetParametro(scheduling.getDtEnd());
			}
			
			if (scheduling.getDtStartPerid() != null) {
				
				sql.SetParametro(scheduling.getDtStartPerid());
				sql.SetParametro(scheduling.getDtStartPerid());
			}
			
			if (scheduling.getDtEndPerid() != null) {
				
				sql.SetParametro(scheduling.getDtEndPerid());
				sql.SetParametro(scheduling.getDtEndPerid());
			}
			
			if (scheduling.getDtStartPresent() != null) {
				
				sql.SetParametro(scheduling.getDtStartPresent());
				sql.SetParametro(scheduling.getDtStartPresent());
			}
			
			if (scheduling.getDtEndPresent() != null) {
				
				sql.SetParametro(scheduling.getDtEndPresent());
				sql.SetParametro(scheduling.getDtEndPresent());
			}
			
			if (scheduling.getDtStartDelay() != null) {
		
				sql.SetParametro(scheduling.getDtStartDelay());
				sql.SetParametro(scheduling.getDtStartDelay());
			}
			
			if (scheduling.getDtEndDelay() != null) {
			
				sql.SetParametro(scheduling.getDtEndDelay());
				sql.SetParametro(scheduling.getDtEndDelay());
			}
			
			if (scheduling.getActive() != null) {
			
				sql.SetParametro(scheduling.getActive());
			}
			
			ResultSet resultSet = sql.Select();
			
			try {
			
				Gson gson = new Gson();
				
				return Retorno.Sucesso(gson.toJson(Util.ResultSetToArrayList(resultSet))) ;
				
			}
			finally {
			
				resultSet.close();
				
			}
			
		}
		finally {
			
			sql.Close();

			
		}
		
	}
}
