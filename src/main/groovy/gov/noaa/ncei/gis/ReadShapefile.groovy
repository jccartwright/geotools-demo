package gov.noaa.ncei.gis

import org.geotools.filter.text.cql2.CQL
import java.io.File
import org.geotools.data.FileDataStore
import org.geotools.data.FileDataStoreFinder
import org.geotools.data.simple.SimpleFeatureSource
import org.geotools.swing.data.JFileDataStoreChooser;
import org.geotools.data.shapefile.*
import org.opengis.feature.simple.*
import org.geotools.feature.collection.*
import org.geotools.feature.*
import org.geotools.data.*
import org.opengis.filter.*


public class ReadShapefile {

    public static void main(String[] args) throws Exception {
        // display a data store file chooser dialog for shapefiles
        //File file = JFileDataStoreChooser.showOpenFile("shp", null);
        File file = new File('/Users/jcc/Documents/shapefiles/cities.shp')
        assert file != null && file.exists()

        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        String typeName = store.getTypeNames()[0];

//        SimpleFeatureSource source = store.getFeatureSource();
        FeatureSource<SimpleFeatureType, SimpleFeature> source = store.getFeatureSource(typeName);


//        Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")
        Filter filter = CQL.toFilter("NAME like 'D%'")

        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
        FeatureIterator<SimpleFeature> features = collection.features()
        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            println "${feature.getAttribute('NAME')}: ${feature.getDefaultGeometryProperty().getValue()}"
        }
    }

}