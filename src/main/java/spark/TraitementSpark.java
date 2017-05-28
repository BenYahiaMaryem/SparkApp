package spark;

import static java.util.Collections.singletonList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.execution.datasources.Partition;
import org.bson.Document;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.WriteConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import com.mongodb.util.JSON;

public class TraitementSpark {

	public static String traitement(String i)
	{
		int id=Integer.parseInt(i);
		System.out.println("id user"+id);
		SparkSession spark = SparkSession.builder()
			      .master("local")
			      .appName("MongoSparkConnectorIntro")
			      //.config("spark.mongodb.input.uri", "mongodb://127.0.0.1/test.user")
			      
			    	  .config("spark.mongodb.input.uri","mongodb://admin:admin@ds155091.mlab.com:55091/sportifydb.users")
			     // .config("spark.mongodb.output.uri", "mongodb://127.0.0.1/test.responseCollection")
			      .getOrCreate();

			    // Create a JavaSparkContext using the SparkSession's SparkContext object
			    JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
			    
			    
			    /*Start Example: Read data from MongoDB************************/
			    JavaMongoRDD<Document> rdd = MongoSpark.load(jsc);
			    System.out.println("avant erreur");
			    System.out.println(rdd.count());
			    /*End Example**************************************************/
			   try{ JavaMongoRDD<Document> aggregatedRddUser = rdd.withPipeline(
			    	      singletonList(
			    	        Document.parse("{ $match: {id:"+id+" } }")));
			    Document jsono=aggregatedRddUser.first();
		
			  System.out.println("array "+jsono);
			    JavaMongoRDD<Document> aggregatedRdd = rdd.withPipeline(
			    	      singletonList(
			    	        Document.parse("{ $match: {prefs: {  $in:"+ jsono.get("prefs")+"} } }")));

			   
			    System.out.println("please");
			 
			    List<Document> listjson=aggregatedRdd.collect();
			    String listUsers="["+listjson.get(0).toJson();
			
			for(int j=1;j< listjson.size();j++)
			{
				listUsers =listUsers+","+listjson.get(j).toJson();
			}
			listUsers=listUsers+"]";
			jsc.close();
			 return listUsers; 
			   }
			   catch(Exception e){
				   System.out.println("error "+e.toString());
				   return e.toString();
			   }
			
			    
			    
			    
			 
					  
	}
}
