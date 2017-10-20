package ch.heigvd.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import ch.heigvd.model.entities.KebabShop;

/**
 *
 * @author Guillaume Milani & Maxime Guillod
 */
@Stateless
public class KebabShopManager implements KebabShopManagerLocal {

    @Resource(lookup = "java:/amtDS")
    private DataSource dataSource;

    @Override
    public KebabShop getRandomKebabShop() {
        return new KebabShop("Dylan", "Grand-Rue 1", "Yverdon", "Switzerland", "0", "today", 9.85f);
    }

    @Override
    public List<KebabShop> findAllKebabShops() {
        List<KebabShop> kebabShops = new ArrayList<>();

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Kebab_Shop");
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                kebabShops.add(getKebabShopFromResult(resultSet));
            }
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(KebabShopManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return kebabShops;
    }

    private KebabShop getKebabShopFromResult(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String street = resultSet.getString("street");
        String city = resultSet.getString("city");
        String country = resultSet.getString("country");
        String phone = resultSet.getString("phone");
        float kebabAveragePrice = resultSet.getFloat("kebab_average_price");
        String creationDate = resultSet.getString("creation_date");
        KebabShop kebabShop = new KebabShop(id, name, street, city, country, phone, creationDate, kebabAveragePrice);

        return kebabShop;
    }

    @Override
    public void addKebabShop(KebabShop kebabShop) throws Exception {
        try {
            Connection connection = dataSource.getConnection();
            String query = String.format(
                    "INSERT INTO `Kebab_Shop` (`name`, `street`, `city`, `country`, `phone`, `kebab_average_price`, `creation_date`) "
                    + "VALUES ('%s', '%s', '%s', '%s', '%s', %f, '%s');",
                    kebabShop.getName(), kebabShop.getStreet(), kebabShop.getCity(), kebabShop.getCountry(), kebabShop.getPhone(), kebabShop.getKebabAveragePrice(), kebabShop.getCreationDate()
            );
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.executeUpdate();
            
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(KebabShopManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}