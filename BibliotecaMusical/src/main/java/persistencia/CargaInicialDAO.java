/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisf
 */
public class CargaInicialDAO {

    private static final String MONGO_URI = "mongodb://localhost:27017";
    private static final String NOMBRE_BD = "bibliotecaMusical";

    private final MongoDatabase baseDeDatos;

    /**
     * Usa la conexion por defecto (localhost:27017 / bibliotecaMusical).
     */
    public CargaInicialDAO() {
        IConexionDAO conexionDAO = new ConexionDAO();
        this.baseDeDatos = conexionDAO.conexion();
    }

    /**
     * Permite reutilizar una MongoDatabase ya abierta en otra parte del
     * proyecto.
     */
    public CargaInicialDAO(IConexionDAO conexionDAO) {
        this.baseDeDatos = conexionDAO.conexion();
    }

    /**
     * Metodo a invocar desde el boton de la pantalla. Inserta generos, artistas
     * y albumes en 3 consultas en total.
     */
    public void insertarDatosIniciales() {
        try {
            int generosProcesados = insertarGeneros();
            int artistasInsertados = insertarArtistas();
            int albumesInsertados = insertarAlbumes();

            System.out.println("Carga inicial completada:");
            System.out.println("  Generos procesados (bulkWrite/upsert): " + generosProcesados);
            System.out.println("  Artistas insertados (insertMany): " + artistasInsertados);
            System.out.println("  Albumes insertados (insertMany): " + albumesInsertados);
        } catch (Exception ex) {
            System.err.println("Error durante la carga inicial: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private int insertarGeneros() {
        List<Document> generos = parsearArray(GENEROS_JSON);

        List<WriteModel<Document>> operaciones = new ArrayList<>();
        for (Document genero : generos) {
            Bson filtro = Filters.eq("_id", genero.getObjectId("_id"));
            Bson actualizacion = Updates.setOnInsert("nombre", genero.getString("nombre"));
            operaciones.add(new UpdateOneModel<>(filtro, actualizacion, new UpdateOptions().upsert(true)));
        }

        MongoCollection<Document> coleccion = baseDeDatos.getCollection("generos");
        coleccion.bulkWrite(operaciones);
        return operaciones.size();
    }

    // ==========================================================================
    // 2. Coleccion: artistas -> insertMany, 1 sola consulta
    // ==========================================================================
    private int insertarArtistas() {
        List<Document> artistas = parsearArray(ARTISTAS_JSON);
        baseDeDatos.getCollection("artistas").insertMany(artistas);
        return artistas.size();
    }

    // ==========================================================================
    // 3. Coleccion: albumes -> insertMany, 1 sola consulta
    // ==========================================================================
    private int insertarAlbumes() {
        List<Document> albumes = parsearArray(ALBUMES_JSON);

        // A cada cancion se le asigna su propio ObjectId antes de insertar,
        // igual que hacia el script original con "_id": new ObjectId().
        for (Document album : albumes) {
            @SuppressWarnings("unchecked")
            List<Document> canciones = (List<Document>) album.get("canciones");
            if (canciones != null) {
                for (Document cancion : canciones) {
                    cancion.put("_id", new ObjectId());
                }
            }
        }

        baseDeDatos.getCollection("albumes").insertMany(albumes);
        return albumes.size();
    }

    /**
     * Convierte un arreglo JSON (formato extendido de Mongo: ObjectId(...),
     * ISODate(...)) en una List<Document>, envolviendolo para que
     * Document.parse lo acepte.
     */
    @SuppressWarnings("unchecked")
    private List<Document> parsearArray(String jsonArray) {
        Document envoltura = Document.parse("{ \"datos\": " + jsonArray + " }");
        return (List<Document>) envoltura.get("datos");
    }

    // ==========================================================================
    // Datos de carga inicial (mismos datos que insertar_artistas_optimizado.js)
    // ==========================================================================
    private static final String GENEROS_JSON = """
[
  { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
  { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
  { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6003"), "nombre": "K-pop" },
  { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6004"), "nombre": "Metal" },
  { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6005"), "nombre": "Pop Latino" },
  { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6006"), "nombre": "Rock" },
  { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6007"), "nombre": "Pop" },
  { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6008"), "nombre": "Rock en Español" },
  { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6009"), "nombre": "Flamenco Pop" },
  { "_id": ObjectId("668c9c9b1f4a2c3d4e5f600a"), "nombre": "Nu Metal" }
]
""";

    private static final String ARTISTAS_JSON = """
[
  {
    "nombre": "BTS",
    "imagen": "imagenBTS.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6003"), "nombre": "K-pop" },
    "tipo": "Banda",
    "integrantes": [
      { "nombres": "Nam-joon (RM)", "apellidoPaterno": "Kim", "apellidoMaterno": "", "rol": "Rapero", "fechaIngreso": ISODate("2013-06-13") },
      { "nombres": "Seok-jin (Jin)", "apellidoPaterno": "Kim", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("2013-06-13") },
      { "nombres": "Yoon-gi (Suga)", "apellidoPaterno": "Min", "apellidoMaterno": "", "rol": "Rapero", "fechaIngreso": ISODate("2013-06-13") },
      { "nombres": "Ho-seok (J-Hope)", "apellidoPaterno": "Jung", "apellidoMaterno": "", "rol": "Rapero", "fechaIngreso": ISODate("2013-06-13") },
      { "nombres": "Ji-min (Jimin)", "apellidoPaterno": "Park", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("2013-06-13") },
      { "nombres": "Tae-hyung (V)", "apellidoPaterno": "Kim", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("2013-06-13") },
      { "nombres": "Jung-kook (Jungkook)", "apellidoPaterno": "Jeon", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("2013-06-13") }
    ]
  },
  {
    "nombre": "Metallica",
    "imagen": "imagenMetallica.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6004"), "nombre": "Metal" },
    "tipo": "Banda",
    "integrantes": [
      { "nombres": "James Alan", "apellidoPaterno": "Hetfield", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("1981-10-28") },
      { "nombres": "Lars", "apellidoPaterno": "Ulrich", "apellidoMaterno": "", "rol": "Baterista", "fechaIngreso": ISODate("1981-10-28") },
      { "nombres": "Kirk Lee", "apellidoPaterno": "Hammett", "apellidoMaterno": "", "rol": "Guitarrista", "fechaIngreso": ISODate("1983-04-01") },
      { "nombres": "Robert Emilio", "apellidoPaterno": "Trujillo", "apellidoMaterno": "", "rol": "Bajista", "fechaIngreso": ISODate("2003-02-24") }
    ]
  },
  {
    "nombre": "Coldplay",
    "imagen": "imagenColdplay.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "tipo": "Banda",
    "integrantes": [
      { "nombres": "Christopher Anthony John", "apellidoPaterno": "Martin", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("1998-01-01") },
      { "nombres": "Jonathan Mark", "apellidoPaterno": "Buckland", "apellidoMaterno": "", "rol": "Guitarrista", "fechaIngreso": ISODate("1998-01-01") },
      { "nombres": "Guy Rupert", "apellidoPaterno": "Berryman", "apellidoMaterno": "", "rol": "Bajista", "fechaIngreso": ISODate("1998-01-01") },
      { "nombres": "William", "apellidoPaterno": "Champion", "apellidoMaterno": "", "rol": "Baterista", "fechaIngreso": ISODate("1998-01-01") }
    ]
  },
  {
    "nombre": "Shakira",
    "imagen": "imagenShakira.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6005"), "nombre": "Pop Latino" },
    "tipo": "Solista",
    "integrantes": [
      { "nombres": "Shakira Isabel", "apellidoPaterno": "Mebarak", "apellidoMaterno": "Ripoll", "rol": "Vocalista", "fechaIngreso": ISODate("1990-01-01") }
    ]
  },
  {
    "nombre": "Bad Bunny",
    "imagen": "imagenBadBunny.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "tipo": "Solista",
    "integrantes": [
      { "nombres": "Benito Antonio", "apellidoPaterno": "Martínez", "apellidoMaterno": "Ocasio", "rol": "Rapero", "fechaIngreso": ISODate("2016-01-01") }
    ]
  },
  {
    "nombre": "Queen",
    "imagen": "imagenQueen.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6006"), "nombre": "Rock" },
    "tipo": "Banda",
    "integrantes": [
      { "nombres": "Farrokh (Freddie Mercury)", "apellidoPaterno": "Bulsara", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("1970-06-27") },
      { "nombres": "Brian Harold", "apellidoPaterno": "May", "apellidoMaterno": "", "rol": "Guitarrista", "fechaIngreso": ISODate("1970-06-27") },
      { "nombres": "Roger Meddows", "apellidoPaterno": "Taylor", "apellidoMaterno": "", "rol": "Baterista", "fechaIngreso": ISODate("1970-06-27") },
      { "nombres": "John Richard", "apellidoPaterno": "Deacon", "apellidoMaterno": "", "rol": "Bajista", "fechaIngreso": ISODate("1971-02-01") }
    ]
  },
  {
    "nombre": "ABBA",
    "imagen": "imagenABBA.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6007"), "nombre": "Pop" },
    "tipo": "Banda",
    "integrantes": [
      { "nombres": "Agnetha", "apellidoPaterno": "Fältskog", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("1972-11-01") },
      { "nombres": "Björn", "apellidoPaterno": "Ulvaeus", "apellidoMaterno": "", "rol": "Guitarrista", "fechaIngreso": ISODate("1972-11-01") },
      { "nombres": "Benny", "apellidoPaterno": "Andersson", "apellidoMaterno": "", "rol": "Tecladista", "fechaIngreso": ISODate("1972-11-01") },
      { "nombres": "Anni-Frid", "apellidoPaterno": "Lyngstad", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("1972-11-01") }
    ]
  },
  {
    "nombre": "Maná",
    "imagen": "imagenMana.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6008"), "nombre": "Rock en Español" },
    "tipo": "Banda",
    "integrantes": [
      { "nombres": "José Fernando", "apellidoPaterno": "Olvera", "apellidoMaterno": "Sierra", "rol": "Vocalista", "fechaIngreso": ISODate("1986-01-01") },
      { "nombres": "Alexander", "apellidoPaterno": "González", "apellidoMaterno": "Estrada", "rol": "Baterista", "fechaIngreso": ISODate("1986-01-01") },
      { "nombres": "Sergio", "apellidoPaterno": "Vallín", "apellidoMaterno": "Vázquez", "rol": "Guitarrista", "fechaIngreso": ISODate("1995-01-01") },
      { "nombres": "Juan Diego", "apellidoPaterno": "Calleros", "apellidoMaterno": "Alcaraz", "rol": "Bajista", "fechaIngreso": ISODate("1986-01-01") }
    ]
  },
  {
    "nombre": "Café Tacvba",
    "imagen": "imagenCafeTacvba.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "tipo": "Banda",
    "integrantes": [
      { "nombres": "Rubén Isaac", "apellidoPaterno": "Albarrán", "apellidoMaterno": "Ortega", "rol": "Vocalista", "fechaIngreso": ISODate("1989-01-01") },
      { "nombres": "Emmanuel", "apellidoPaterno": "Del Real", "apellidoMaterno": "Díaz", "rol": "Tecladista", "fechaIngreso": ISODate("1989-01-01") },
      { "nombres": "José Alfredo (Joselo)", "apellidoPaterno": "Rangel", "apellidoMaterno": "Arroyo", "rol": "Guitarrista", "fechaIngreso": ISODate("1989-01-01") },
      { "nombres": "Enrique (Quique)", "apellidoPaterno": "Rangel", "apellidoMaterno": "Arroyo", "rol": "Bajista", "fechaIngreso": ISODate("1989-01-01") }
    ]
  },
  {
    "nombre": "Rosalía",
    "imagen": "imagenRosalia.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6009"), "nombre": "Flamenco Pop" },
    "tipo": "Solista",
    "integrantes": [
      { "nombres": "Rosalía", "apellidoPaterno": "Vila", "apellidoMaterno": "Tobella", "rol": "Vocalista", "fechaIngreso": ISODate("2017-01-01") }
    ]
  },
  {
    "nombre": "Daddy Yankee",
    "imagen": "imagenDaddyYankee.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "tipo": "Solista",
    "integrantes": [
      { "nombres": "Ramón Luis", "apellidoPaterno": "Ayala", "apellidoMaterno": "Rodríguez", "rol": "Rapero", "fechaIngreso": ISODate("1994-01-01") }
    ]
  },
  {
    "nombre": "Imagine Dragons",
    "imagen": "imagenImagineDragons.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "tipo": "Banda",
    "integrantes": [
      { "nombres": "Daniel Coulter", "apellidoPaterno": "Reynolds", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("2008-01-01") },
      { "nombres": "Daniel Wayne", "apellidoPaterno": "Sermon", "apellidoMaterno": "", "rol": "Guitarrista", "fechaIngreso": ISODate("2008-01-01") },
      { "nombres": "Benjamin", "apellidoPaterno": "McKee", "apellidoMaterno": "", "rol": "Bajista", "fechaIngreso": ISODate("2010-01-01") },
      { "nombres": "Daniel", "apellidoPaterno": "Platzman", "apellidoMaterno": "", "rol": "Baterista", "fechaIngreso": ISODate("2011-01-01") }
    ]
  },
  {
    "nombre": "Linkin Park",
    "imagen": "imagenLinkinPark.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f600a"), "nombre": "Nu Metal" },
    "tipo": "Banda",
    "integrantes": [
      { "nombres": "Chester Charles", "apellidoPaterno": "Bennington", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("1999-01-01") },
      { "nombres": "Michael Kenji", "apellidoPaterno": "Shinoda", "apellidoMaterno": "", "rol": "Rapero", "fechaIngreso": ISODate("1996-01-01") },
      { "nombres": "Bradford Philip", "apellidoPaterno": "Delson", "apellidoMaterno": "", "rol": "Guitarrista", "fechaIngreso": ISODate("1996-01-01") },
      { "nombres": "Robert Gregory", "apellidoPaterno": "Bourdon", "apellidoMaterno": "", "rol": "Baterista", "fechaIngreso": ISODate("1996-01-01") }
    ]
  },
  {
    "nombre": "The Beatles",
    "imagen": "imagenBeatles.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6006"), "nombre": "Rock" },
    "tipo": "Banda",
    "integrantes": [
      { "nombres": "John Winston", "apellidoPaterno": "Lennon", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("1960-08-01") },
      { "nombres": "James Paul", "apellidoPaterno": "McCartney", "apellidoMaterno": "", "rol": "Vocalista", "fechaIngreso": ISODate("1960-08-01") },
      { "nombres": "George", "apellidoPaterno": "Harrison", "apellidoMaterno": "", "rol": "Guitarrista", "fechaIngreso": ISODate("1960-08-01") },
      { "nombres": "Richard (Ringo Starr)", "apellidoPaterno": "Starkey", "apellidoMaterno": "", "rol": "Baterista", "fechaIngreso": ISODate("1962-08-01") }
    ]
  },
  {
    "nombre": "Karol G",
    "imagen": "imagenKarolG.jpg",
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "tipo": "Solista",
    "integrantes": [
      { "nombres": "Carolina", "apellidoPaterno": "Giraldo", "apellidoMaterno": "Navarro", "rol": "Vocalista", "fechaIngreso": ISODate("2007-01-01") }
    ]
  }
]
""";

    private static final String ALBUMES_JSON = """
[
  {
    "nombreArtista": "BTS",
    "nombre": "Map of the Soul: Persona",
    "fechaLanzamiento": ISODate("2019-04-12"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6003"), "nombre": "K-pop" },
    "imagen": "imagenMapOfTheSoulPersona.jpg",
    "canciones": [
      { "nombre": "Boy With Luv", "duracion": "03:28" },
      { "nombre": "Mikrokosmos", "duracion": "03:52" },
      { "nombre": "Home", "duracion": "03:31" }
    ]
  },
  {
    "nombreArtista": "BTS",
    "nombre": "Map of the Soul: 7",
    "fechaLanzamiento": ISODate("2020-02-21"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6003"), "nombre": "K-pop" },
    "imagen": "imagenMapOfTheSoul7.jpg",
    "canciones": [
      { "nombre": "ON", "duracion": "04:05" },
      { "nombre": "Black Swan", "duracion": "03:38" },
      { "nombre": "00:00 (Zero O'Clock)", "duracion": "03:26" }
    ]
  },
  {
    "nombreArtista": "BTS",
    "nombre": "BE",
    "fechaLanzamiento": ISODate("2020-11-20"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6003"), "nombre": "K-pop" },
    "imagen": "imagenBE.jpg",
    "canciones": [
      { "nombre": "Life Goes On", "duracion": "03:23" },
      { "nombre": "Fly To My Room", "duracion": "03:16" },
      { "nombre": "Blue & Grey", "duracion": "04:19" }
    ]
  },
  {
    "nombreArtista": "Metallica",
    "nombre": "Master of Puppets",
    "fechaLanzamiento": ISODate("1986-03-03"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6004"), "nombre": "Metal" },
    "imagen": "imagenMasterOfPuppets.jpg",
    "canciones": [
      { "nombre": "Battery", "duracion": "05:11" },
      { "nombre": "Master of Puppets", "duracion": "08:35" },
      { "nombre": "Welcome Home (Sanitarium)", "duracion": "06:31" }
    ]
  },
  {
    "nombreArtista": "Metallica",
    "nombre": "...And Justice for All",
    "fechaLanzamiento": ISODate("1988-08-25"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6004"), "nombre": "Metal" },
    "imagen": "imagenAndJusticeForAll.jpg",
    "canciones": [
      { "nombre": "Blackened", "duracion": "06:42" },
      { "nombre": "...And Justice for All", "duracion": "09:45" },
      { "nombre": "One", "duracion": "07:26" }
    ]
  },
  {
    "nombreArtista": "Metallica",
    "nombre": "Metallica (Black Album)",
    "fechaLanzamiento": ISODate("1991-08-12"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6004"), "nombre": "Metal" },
    "imagen": "imagenBlackAlbum.jpg",
    "canciones": [
      { "nombre": "Enter Sandman", "duracion": "05:31" },
      { "nombre": "Sad But True", "duracion": "05:24" },
      { "nombre": "Nothing Else Matters", "duracion": "06:28" }
    ]
  },
  {
    "nombreArtista": "Coldplay",
    "nombre": "Parachutes",
    "fechaLanzamiento": ISODate("2000-07-10"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "imagen": "imagenParachutes.jpg",
    "canciones": [
      { "nombre": "Yellow", "duracion": "04:29" },
      { "nombre": "Trouble", "duracion": "04:33" },
      { "nombre": "Shiver", "duracion": "05:00" }
    ]
  },
  {
    "nombreArtista": "Coldplay",
    "nombre": "A Rush of Blood to the Head",
    "fechaLanzamiento": ISODate("2002-08-26"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "imagen": "imagenRushOfBlood.jpg",
    "canciones": [
      { "nombre": "Clocks", "duracion": "05:07" },
      { "nombre": "The Scientist", "duracion": "04:09" },
      { "nombre": "In My Place", "duracion": "03:48" }
    ]
  },
  {
    "nombreArtista": "Coldplay",
    "nombre": "Viva la Vida",
    "fechaLanzamiento": ISODate("2008-06-12"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "imagen": "imagenVivaLaVida.jpg",
    "canciones": [
      { "nombre": "Viva la Vida", "duracion": "04:02" },
      { "nombre": "Violet Hill", "duracion": "03:42" },
      { "nombre": "Life in Technicolor", "duracion": "02:16" }
    ]
  },
  {
    "nombreArtista": "Shakira",
    "nombre": "Pies Descalzos",
    "fechaLanzamiento": ISODate("1995-02-14"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6005"), "nombre": "Pop Latino" },
    "imagen": "imagenPiesDescalzos.jpg",
    "canciones": [
      { "nombre": "Estoy Aquí", "duracion": "03:34" },
      { "nombre": "Un Poco de Amor", "duracion": "04:10" },
      { "nombre": "Antología", "duracion": "05:02" }
    ]
  },
  {
    "nombreArtista": "Shakira",
    "nombre": "Dónde Están los Ladrones?",
    "fechaLanzamiento": ISODate("1998-09-22"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6005"), "nombre": "Pop Latino" },
    "imagen": "imagenDondeEstanLosLadrones.jpg",
    "canciones": [
      { "nombre": "Ciega, Sordomuda", "duracion": "03:40" },
      { "nombre": "Inevitable", "duracion": "04:32" },
      { "nombre": "Ojos Así", "duracion": "05:03" }
    ]
  },
  {
    "nombreArtista": "Shakira",
    "nombre": "Laundry Service",
    "fechaLanzamiento": ISODate("2001-11-13"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6005"), "nombre": "Pop Latino" },
    "imagen": "imagenLaundryService.jpg",
    "canciones": [
      { "nombre": "Whenever, Wherever", "duracion": "03:21" },
      { "nombre": "Underneath Your Clothes", "duracion": "03:57" },
      { "nombre": "Suerte", "duracion": "03:21" }
    ]
  },
  {
    "nombreArtista": "Bad Bunny",
    "nombre": "YHLQMDLG",
    "fechaLanzamiento": ISODate("2020-02-29"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "imagen": "imagenYHLQMDLG.jpg",
    "canciones": [
      { "nombre": "Yo Perreo Sola", "duracion": "02:53" },
      { "nombre": "La Difícil", "duracion": "03:35" },
      { "nombre": "Safaera", "duracion": "03:49" }
    ]
  },
  {
    "nombreArtista": "Bad Bunny",
    "nombre": "El Último Tour Del Mundo",
    "fechaLanzamiento": ISODate("2020-11-27"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "imagen": "imagenElUltimoTour.jpg",
    "canciones": [
      { "nombre": "Dákiti", "duracion": "03:15" },
      { "nombre": "Booker T", "duracion": "02:53" },
      { "nombre": "La Noche de Anoche", "duracion": "03:29" }
    ]
  },
  {
    "nombreArtista": "Bad Bunny",
    "nombre": "Un Verano Sin Ti",
    "fechaLanzamiento": ISODate("2022-05-06"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "imagen": "imagenUnVeranoSinTi.jpg",
    "canciones": [
      { "nombre": "Tití Me Preguntó", "duracion": "04:03" },
      { "nombre": "Me Porto Bonito", "duracion": "03:02" },
      { "nombre": "Ojitos Lindos", "duracion": "04:15" }
    ]
  },
  {
    "nombreArtista": "Queen",
    "nombre": "A Night at the Opera",
    "fechaLanzamiento": ISODate("1975-11-21"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6006"), "nombre": "Rock" },
    "imagen": "imagenNightAtTheOpera.jpg",
    "canciones": [
      { "nombre": "Bohemian Rhapsody", "duracion": "05:55" },
      { "nombre": "Love of My Life", "duracion": "03:38" },
      { "nombre": "You're My Best Friend", "duracion": "02:52" }
    ]
  },
  {
    "nombreArtista": "Queen",
    "nombre": "News of the World",
    "fechaLanzamiento": ISODate("1977-10-28"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6006"), "nombre": "Rock" },
    "imagen": "imagenNewsOfTheWorld.jpg",
    "canciones": [
      { "nombre": "We Will Rock You", "duracion": "02:02" },
      { "nombre": "We Are the Champions", "duracion": "02:59" },
      { "nombre": "Sheer Heart Attack", "duracion": "03:22" }
    ]
  },
  {
    "nombreArtista": "Queen",
    "nombre": "The Game",
    "fechaLanzamiento": ISODate("1980-06-30"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6006"), "nombre": "Rock" },
    "imagen": "imagenTheGame.jpg",
    "canciones": [
      { "nombre": "Another One Bites the Dust", "duracion": "03:35" },
      { "nombre": "Crazy Little Thing Called Love", "duracion": "02:43" },
      { "nombre": "Play the Game", "duracion": "03:32" }
    ]
  },
  {
    "nombreArtista": "ABBA",
    "nombre": "Arrival",
    "fechaLanzamiento": ISODate("1976-10-11"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6007"), "nombre": "Pop" },
    "imagen": "imagenArrival.jpg",
    "canciones": [
      { "nombre": "Dancing Queen", "duracion": "03:51" },
      { "nombre": "Knowing Me, Knowing You", "duracion": "04:04" },
      { "nombre": "Money, Money, Money", "duracion": "03:08" }
    ]
  },
  {
    "nombreArtista": "ABBA",
    "nombre": "ABBA: The Album",
    "fechaLanzamiento": ISODate("1977-12-12"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6007"), "nombre": "Pop" },
    "imagen": "imagenTheAlbum.jpg",
    "canciones": [
      { "nombre": "The Name of the Game", "duracion": "04:56" },
      { "nombre": "Take a Chance on Me", "duracion": "04:05" },
      { "nombre": "Eagle", "duracion": "05:56" }
    ]
  },
  {
    "nombreArtista": "ABBA",
    "nombre": "Voulez-Vous",
    "fechaLanzamiento": ISODate("1979-04-23"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6007"), "nombre": "Pop" },
    "imagen": "imagenVoulezVous.jpg",
    "canciones": [
      { "nombre": "Voulez-Vous", "duracion": "04:15" },
      { "nombre": "Chiquitita", "duracion": "05:26" },
      { "nombre": "Does Your Mother Know", "duracion": "03:14" }
    ]
  },
  {
    "nombreArtista": "Maná",
    "nombre": "Dónde Jugarán los Niños?",
    "fechaLanzamiento": ISODate("1992-09-21"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6008"), "nombre": "Rock en Español" },
    "imagen": "imagenDondeJugaranLosNinos.jpg",
    "canciones": [
      { "nombre": "Oye Mi Amor", "duracion": "04:24" },
      { "nombre": "Rayando el Sol", "duracion": "04:57" },
      { "nombre": "De Pies a Cabeza", "duracion": "04:04" }
    ]
  },
  {
    "nombreArtista": "Maná",
    "nombre": "Cuando los Ángeles Lloran",
    "fechaLanzamiento": ISODate("1995-09-19"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6008"), "nombre": "Rock en Español" },
    "imagen": "imagenCuandoLosAngelesLloran.jpg",
    "canciones": [
      { "nombre": "Cuando los Ángeles Lloran", "duracion": "05:07" },
      { "nombre": "Vivir sin Aire", "duracion": "04:34" },
      { "nombre": "Como Te Deseo", "duracion": "04:15" }
    ]
  },
  {
    "nombreArtista": "Maná",
    "nombre": "Sueños Líquidos",
    "fechaLanzamiento": ISODate("1997-10-14"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6008"), "nombre": "Rock en Español" },
    "imagen": "imagenSuenosLiquidos.jpg",
    "canciones": [
      { "nombre": "En el Muelle de San Blas", "duracion": "06:33" },
      { "nombre": "Clavado en un Bar", "duracion": "04:23" },
      { "nombre": "Hundido en un Rincón", "duracion": "04:44" }
    ]
  },
  {
    "nombreArtista": "Café Tacvba",
    "nombre": "Re",
    "fechaLanzamiento": ISODate("1994-06-01"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "imagen": "imagenRe.jpg",
    "canciones": [
      { "nombre": "El Aparato", "duracion": "03:30" },
      { "nombre": "Trópico de Cáncer", "duracion": "04:20" },
      { "nombre": "La Ingrata", "duracion": "04:05" }
    ]
  },
  {
    "nombreArtista": "Café Tacvba",
    "nombre": "Avalancha de Éxitos",
    "fechaLanzamiento": ISODate("1996-01-01"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "imagen": "imagenAvalanchaDeExitos.jpg",
    "canciones": [
      { "nombre": "Chilanga Banda", "duracion": "03:30" },
      { "nombre": "Las Flores", "duracion": "03:47" },
      { "nombre": "Nubes", "duracion": "04:02" }
    ]
  },
  {
    "nombreArtista": "Café Tacvba",
    "nombre": "Cuatro Caminos",
    "fechaLanzamiento": ISODate("2003-04-22"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "imagen": "imagenCuatroCaminos.jpg",
    "canciones": [
      { "nombre": "Eres", "duracion": "04:15" },
      { "nombre": "Que No", "duracion": "05:12" },
      { "nombre": "Desperté", "duracion": "03:58" }
    ]
  },
  {
    "nombreArtista": "Rosalía",
    "nombre": "Los Ángeles",
    "fechaLanzamiento": ISODate("2017-01-27"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6009"), "nombre": "Flamenco Pop" },
    "imagen": "imagenLosAngeles.jpg",
    "canciones": [
      { "nombre": "Catalina", "duracion": "03:12" },
      { "nombre": "De Plata", "duracion": "03:45" },
      { "nombre": "Que No Salga la Luna", "duracion": "03:33" }
    ]
  },
  {
    "nombreArtista": "Rosalía",
    "nombre": "El Mal Querer",
    "fechaLanzamiento": ISODate("2018-11-02"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6009"), "nombre": "Flamenco Pop" },
    "imagen": "imagenElMalQuerer.jpg",
    "canciones": [
      { "nombre": "Malamente", "duracion": "02:43" },
      { "nombre": "Pienso en Tu Mirá", "duracion": "03:07" },
      { "nombre": "Di Mi Nombre", "duracion": "03:15" }
    ]
  },
  {
    "nombreArtista": "Rosalía",
    "nombre": "Motomami",
    "fechaLanzamiento": ISODate("2022-03-18"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6009"), "nombre": "Flamenco Pop" },
    "imagen": "imagenMotomami.jpg",
    "canciones": [
      { "nombre": "Saoko", "duracion": "02:33" },
      { "nombre": "Chicken Teriyaki", "duracion": "02:33" },
      { "nombre": "Hentai", "duracion": "02:22" }
    ]
  },
  {
    "nombreArtista": "Daddy Yankee",
    "nombre": "Barrio Fino",
    "fechaLanzamiento": ISODate("2004-07-13"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "imagen": "imagenBarrioFino.jpg",
    "canciones": [
      { "nombre": "Gasolina", "duracion": "03:13" },
      { "nombre": "Rompe", "duracion": "03:59" },
      { "nombre": "Lo Que Pasó, Pasó", "duracion": "03:59" }
    ]
  },
  {
    "nombreArtista": "Daddy Yankee",
    "nombre": "El Cartel: The Big Boss",
    "fechaLanzamiento": ISODate("2007-06-19"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "imagen": "imagenElCartelBigBoss.jpg",
    "canciones": [
      { "nombre": "Impacto", "duracion": "04:14" },
      { "nombre": "Llamado de Emergencia", "duracion": "04:00" },
      { "nombre": "Como Yo Le Doy", "duracion": "04:03" }
    ]
  },
  {
    "nombreArtista": "Daddy Yankee",
    "nombre": "Prestige",
    "fechaLanzamiento": ISODate("2012-08-31"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "imagen": "imagenPrestige.jpg",
    "canciones": [
      { "nombre": "Limbo", "duracion": "04:03" },
      { "nombre": "La Despedida", "duracion": "04:32" },
      { "nombre": "Ven Conmigo", "duracion": "03:36" }
    ]
  },
  {
    "nombreArtista": "Imagine Dragons",
    "nombre": "Night Visions",
    "fechaLanzamiento": ISODate("2012-09-04"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "imagen": "imagenNightVisions.jpg",
    "canciones": [
      { "nombre": "Radioactive", "duracion": "03:06" },
      { "nombre": "Demons", "duracion": "02:57" },
      { "nombre": "It's Time", "duracion": "04:19" }
    ]
  },
  {
    "nombreArtista": "Imagine Dragons",
    "nombre": "Smoke + Mirrors",
    "fechaLanzamiento": ISODate("2015-02-17"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "imagen": "imagenSmokeMirrors.jpg",
    "canciones": [
      { "nombre": "I Bet My Life", "duracion": "03:07" },
      { "nombre": "Gold", "duracion": "04:03" },
      { "nombre": "Shots", "duracion": "03:07" }
    ]
  },
  {
    "nombreArtista": "Imagine Dragons",
    "nombre": "Evolve",
    "fechaLanzamiento": ISODate("2017-06-23"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6001"), "nombre": "Rock Alternativo" },
    "imagen": "imagenEvolve.jpg",
    "canciones": [
      { "nombre": "Believer", "duracion": "03:24" },
      { "nombre": "Thunder", "duracion": "03:07" },
      { "nombre": "Whatever It Takes", "duracion": "03:21" }
    ]
  },
  {
    "nombreArtista": "Linkin Park",
    "nombre": "Hybrid Theory",
    "fechaLanzamiento": ISODate("2000-10-24"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f600a"), "nombre": "Nu Metal" },
    "imagen": "imagenHybridTheory.jpg",
    "canciones": [
      { "nombre": "In the End", "duracion": "03:36" },
      { "nombre": "Crawling", "duracion": "03:29" },
      { "nombre": "Papercut", "duracion": "03:04" }
    ]
  },
  {
    "nombreArtista": "Linkin Park",
    "nombre": "Meteora",
    "fechaLanzamiento": ISODate("2003-03-25"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f600a"), "nombre": "Nu Metal" },
    "imagen": "imagenMeteora.jpg",
    "canciones": [
      { "nombre": "Numb", "duracion": "03:07" },
      { "nombre": "Breaking the Habit", "duracion": "03:16" },
      { "nombre": "Faint", "duracion": "02:42" }
    ]
  },
  {
    "nombreArtista": "Linkin Park",
    "nombre": "Minutes to Midnight",
    "fechaLanzamiento": ISODate("2007-05-14"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f600a"), "nombre": "Nu Metal" },
    "imagen": "imagenMinutesToMidnight.jpg",
    "canciones": [
      { "nombre": "What I've Done", "duracion": "03:26" },
      { "nombre": "Bleed It Out", "duracion": "02:44" },
      { "nombre": "Shadow of the Day", "duracion": "04:52" }
    ]
  },
  {
    "nombreArtista": "The Beatles",
    "nombre": "Sgt. Pepper's Lonely Hearts Club Band",
    "fechaLanzamiento": ISODate("1967-05-26"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6006"), "nombre": "Rock" },
    "imagen": "imagenSgtPeppers.jpg",
    "canciones": [
      { "nombre": "Lucy in the Sky with Diamonds", "duracion": "03:28" },
      { "nombre": "With a Little Help from My Friends", "duracion": "02:44" },
      { "nombre": "A Day in the Life", "duracion": "05:39" }
    ]
  },
  {
    "nombreArtista": "The Beatles",
    "nombre": "Abbey Road",
    "fechaLanzamiento": ISODate("1969-09-26"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6006"), "nombre": "Rock" },
    "imagen": "imagenAbbeyRoad.jpg",
    "canciones": [
      { "nombre": "Come Together", "duracion": "04:19" },
      { "nombre": "Something", "duracion": "03:03" },
      { "nombre": "Here Comes the Sun", "duracion": "03:05" }
    ]
  },
  {
    "nombreArtista": "The Beatles",
    "nombre": "Let It Be",
    "fechaLanzamiento": ISODate("1970-05-08"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6006"), "nombre": "Rock" },
    "imagen": "imagenLetItBe.jpg",
    "canciones": [
      { "nombre": "Let It Be", "duracion": "04:03" },
      { "nombre": "Get Back", "duracion": "03:09" },
      { "nombre": "Across the Universe", "duracion": "03:51" }
    ]
  },
  {
    "nombreArtista": "Karol G",
    "nombre": "Ocean",
    "fechaLanzamiento": ISODate("2019-01-31"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "imagen": "imagenOcean.jpg",
    "canciones": [
      { "nombre": "Punto G", "duracion": "03:16" },
      { "nombre": "Culpables", "duracion": "03:38" },
      { "nombre": "Ahora Me Llama", "duracion": "03:12" }
    ]
  },
  {
    "nombreArtista": "Karol G",
    "nombre": "KG0516",
    "fechaLanzamiento": ISODate("2021-03-05"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "imagen": "imagenKG0516.jpg",
    "canciones": [
      { "nombre": "Bichota", "duracion": "03:15" },
      { "nombre": "200 Copas", "duracion": "03:16" },
      { "nombre": "Location", "duracion": "03:23" }
    ]
  },
  {
    "nombreArtista": "Karol G",
    "nombre": "Mañana Será Bonito",
    "fechaLanzamiento": ISODate("2023-02-24"),
    "genero": { "_id": ObjectId("668c9c9b1f4a2c3d4e5f6002"), "nombre": "Reggaeton" },
    "imagen": "imagenMananaSeraBonito.jpg",
    "canciones": [
      { "nombre": "TQG", "duracion": "03:16" },
      { "nombre": "Provenza", "duracion": "03:12" },
      { "nombre": "Mientras Me Curo del Cora", "duracion": "03:33" }
    ]
  }
]
""";
}
