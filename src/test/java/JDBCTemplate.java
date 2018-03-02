

public class JDBCTemplate {

	public static void main(String[] args) {
		org.springframework.jdbc.datasource.DriverManagerDataSource dataSource = new org.springframework.jdbc.datasource.DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://10.200.130.103:3306/test?useUnicode=true&characterEncoding=utf-8");
		dataSource.setUsername("root");
		dataSource.setPassword("Mysqltest@123098");
//		String sql = "INSERT INTO users(userName,passWord,user_sex) VALUES('abc', 'mmm', MAN)";
//		String sql = "SELECT * FROM users";
		String sql = "SELECT count(1)\r\n" +
				"FROM users\r\n" +
				"WHERE (userName = 'aa' AND user_sex = 'MAN')";
//		String sql = "UPDATE users SET userName='abc',nick_name= WHERE id =30";
//		String sql = "delete from users  WHERE id =30";
//		System.out.println(sql.toLowerCase().split("update")[1].split("set")[0].trim());
		String checkNumSQL="";
		System.out.println(sql.toLowerCase().indexOf("from"));
		System.out.println(sql.replaceAll("\r|\n|\\s", " ").trim().toLowerCase());
		System.out.println(sql.replace("\n", "").trim().toLowerCase().matches("^select.*"));
		if(sql.toLowerCase().indexOf("from") >0  && sql.trim().toLowerCase().matches("^select.*")){
			checkNumSQL="SELECT count(*) "+sql.substring(sql.toLowerCase().indexOf("from"));
		}
		if(sql.toLowerCase().indexOf("where") >0 && sql.trim().toLowerCase().matches("^update.*")){
			String tablename=sql.toLowerCase().split("update")[1].split("set")[0].trim();
			checkNumSQL="SELECT count(*) from "+tablename+" "+sql.substring(sql.toLowerCase().indexOf("where"));
		}
		if(sql.toLowerCase().indexOf("from") >0  && sql.trim().toLowerCase().matches("^delete.*")){
			checkNumSQL="SELECT count(*) "+sql.substring(sql.toLowerCase().indexOf("from"));
		}

		System.out.println(checkNumSQL);
		org.springframework.jdbc.core.JdbcTemplate jdbcTemplate = new org.springframework.jdbc.core.JdbcTemplate(dataSource);
		if (sql.trim().toLowerCase().matches("^select.*") || sql.trim().toLowerCase().matches("^update.*") ||sql.trim().toLowerCase().matches("^delete.*")) {
			Long sqlsize = (Long) jdbcTemplate.queryForMap(checkNumSQL).get("count(*)");
			System.out.println(sqlsize.getClass());
			long size = 1;
			String selectSQL=checkNumSQL.replace("count(*)", "*");
			System.out.println(selectSQL);
			if (sqlsize.longValue() > size) {
				java.util.List<java.util.Map<String, Object>> var1 = jdbcTemplate.queryForList(selectSQL);
				System.out.println(com.alibaba.fastjson.JSON.toJSONString(var1));
			} else if (sqlsize == 1) {
				java.util.Map<String, Object> var = jdbcTemplate.queryForMap(selectSQL);
				System.out.println(com.alibaba.fastjson.JSON.toJSONString(var));
			}
		}
		if(sql.trim().toLowerCase().matches("^insert.*")){
			String deleteSQL="";
		}


	}

}