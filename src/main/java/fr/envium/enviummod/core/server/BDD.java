package fr.envium.enviummod.core.server;

import com.google.common.base.Splitter;
import fr.envium.enviummod.core.server.enums.InventoryType;
import fr.envium.enviummod.core.jobs.JobsEnum;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.system.CallbackI;

import java.sql.*;
import java.util.*;

public class BDD {

    Statement statement;

    public static Connection connection = Connexion.getConnection();
    public static BDD instance;


    public BDD() {
        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("erreur connection create statement");
            }
        }
    }

    public void addInventory(InventoryType inventoryType, List list, PlayerEntity playerEntity) {
        try {
            if (inventoryType.equals(InventoryType.STAFF)) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET inventory_staff = ?, inventory_current = ? WHERE pseudo = ?");
                preparedStatement.setString(1, list.toString());
                preparedStatement.setString(2, InventoryType.STAFF.toString());
                preparedStatement.setString(3, playerEntity.getName().getString().toLowerCase());
                preparedStatement.execute();

                preparedStatement.close();
            } else if (inventoryType.equals(InventoryType.PLAYER)) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET inventory_player = ?, inventory_current = ? WHERE pseudo = ?");
                preparedStatement.setString(1, list.toString());
                preparedStatement.setString(2, InventoryType.PLAYER.toString());
                preparedStatement.setString(3, playerEntity.getName().getString().toLowerCase());
                preparedStatement.execute();

                preparedStatement.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<String> getInventory(PlayerEntity playerEntity, boolean inverse) {
        List<String> stringList = new ArrayList<>();
        try {
            InventoryType inventoryType = getInventoryType(playerEntity, inverse);
            System.out.println(inventoryType);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM player WHERE pseudo = '" + playerEntity.getName().getString().toLowerCase() + "'");
            resultSet.next();

            Splitter splitter = Splitter.on("#,").trimResults().omitEmptyStrings();


            if (inventoryType.equals(InventoryType.STAFF)) {
                Iterable<String> iterableList = splitter.split(resultSet.getString("inventory_staff"));
                iterableList.forEach(stringList::add);
            } else if (inventoryType.equals(InventoryType.PLAYER)) {
                Iterable<String> iterableList = splitter.split(resultSet.getString("inventory_player"));
                iterableList.forEach(stringList::add);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String modifier = stringList.get(0);
        if (modifier.startsWith("[")) {
            modifier = modifier.substring(1);
            stringList.remove(0);
            stringList.add(0, modifier);
        }
        modifier = stringList.get(stringList.size() - 1);
        if (modifier.endsWith("#]")) {
            modifier = modifier.substring(0, modifier.length() - 2);
            stringList.remove(stringList.size() - 1);
            stringList.add(stringList.size() - 1, modifier);
        }


        return stringList;
    }


    public InventoryType getInventoryType(PlayerEntity playerEntity, boolean inverse) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT inventory_current FROM player WHERE pseudo = '" + playerEntity.getName().getString().toLowerCase() + "'");
            resultSet.next();
            if (resultSet.getString("inventory_current").equals("STAFF")) {
                if (inverse) {
                    return InventoryType.PLAYER;
                } else {
                    return InventoryType.STAFF;
                }
            } else if (resultSet.getString("inventory_current").equals("PLAYER")) {
                if (inverse) {
                    return InventoryType.STAFF;
                } else {
                    return InventoryType.PLAYER;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return InventoryType.PLAYER;
    }

    public String getGrade(PlayerEntity playerEntity) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT grade FROM player WHERE pseudo = '" + playerEntity.getName().getString().toLowerCase() + "'");
            resultSet.next();
            return resultSet.getString("grade");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public void addPermissions(PlayerEntity playerEntity, List<String> permissions) {

        List<String> oldPermissions = cleanList(getPermissions(playerEntity));

        System.out.println(oldPermissions);

        permissions.forEach((s) -> {
                if (!oldPermissions.contains(s))
                    oldPermissions.add(s);
        });
        System.out.println(oldPermissions);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET permissions = ? WHERE pseudo = ?");
            preparedStatement.setString(1, oldPermissions.toString());
            preparedStatement.setString(2, playerEntity.getName().getString().toLowerCase());
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removePermissions(PlayerEntity playerEntity, List<String> permissions) {
        List<String> oldPermissions = cleanList(getPermissions(playerEntity));

        /*String modif = oldPermissions.get(0);
        modif = modif.substring(1);
        oldPermissions.remove(0);
        oldPermissions.add(0, modif);

        modif = oldPermissions.get(oldPermissions.size() - 1);
        modif = modif.substring(0, modif.length() - 1);
        oldPermissions.remove(oldPermissions.size() -1);
        oldPermissions.add(modif);*/

        for (Iterator<String> it = permissions.iterator(); it.hasNext(); ) {
            String permission = it.next();
            System.out.println(permission);
            for (int i = 0; i < oldPermissions.size(); i++) {
                if (permission.equals(oldPermissions.get(i))) {
                    oldPermissions.remove(i);
                }
            }

        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET permissions = ? WHERE pseudo = ?");
            preparedStatement.setString(1, oldPermissions.toString());
            preparedStatement.setString(2, playerEntity.getName().getString().toLowerCase());
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public List getPermissions(PlayerEntity playerEntity) {

        try {
            ResultSet resultSet = statement.executeQuery("SELECT permissions FROM player WHERE pseudo = '" + playerEntity.getName().getString().toLowerCase() + "'");
            resultSet.next();

            Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();
            List stringList = new ArrayList();

            Iterable<String> iterableList = splitter.split(resultSet.getString("permissions"));
            iterableList.forEach(stringList::add);

            return stringList;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        System.out.println("[getPermissions] == null");
        return null;
    }

    public static Connection getConnexion() {
        return connection;
    }

    public List cleanList(List<String> list) {
        if (list.size() != 0) {
            String modif = list.get(0);
            modif = modif.substring(1);
            list.remove(0);
            list.add(0, modif);

            if (list.size() != 1) {
                modif = list.get(list.size() - 1);
                modif = modif.substring(0, modif.length() - 1);
                list.remove(list.size() -1);
                list.add(modif);
            } else {
                modif = list.get(0);
                modif = modif.substring(0, modif.length() - 1);
                list.remove(0);
                list.add(modif);
            }
        }
        return list;
    }

    public Map<JobsEnum, Integer> getJobs(PlayerEntity playerEntity) {
        Map<JobsEnum, Integer> jobsIntegerMap = new HashMap<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM player WHERE pseudo = '" + playerEntity.getName().getString().toLowerCase() + "'");
            resultSet.next();

            jobsIntegerMap.put(JobsEnum.MINER, Integer.valueOf(resultSet.getString("miner")));
            jobsIntegerMap.put(JobsEnum.LUMBERJACK, Integer.valueOf(resultSet.getString("bucheron")));
            jobsIntegerMap.put(JobsEnum.CHASSEUR, Integer.valueOf(resultSet.getString("chasseur")));
            return jobsIntegerMap;
        } catch (SQLException throwable) {
            instance  = new BDD();
            throwable.printStackTrace();
        }
        return new HashMap<>();
    }

    public void setJobs(PlayerEntity playerEntity, Map<JobsEnum, Integer> jobsIntegerMap) {
        Map<JobsEnum, Integer> defaultJobs = getJobs(playerEntity);
        int miner = jobsIntegerMap.containsKey(JobsEnum.MINER) ? jobsIntegerMap.get(JobsEnum.MINER) : defaultJobs.get(JobsEnum.MINER);
        int bucheron = jobsIntegerMap.containsKey(JobsEnum.LUMBERJACK) ? jobsIntegerMap.get(JobsEnum.LUMBERJACK) : defaultJobs.get(JobsEnum.LUMBERJACK);
        int chasseur = jobsIntegerMap.containsKey(JobsEnum.CHASSEUR) ? jobsIntegerMap.get(JobsEnum.CHASSEUR) : defaultJobs.get(JobsEnum.CHASSEUR);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET miner = ?, bucheron = ?,chasseur = ? WHERE pseudo = ?");
            preparedStatement.setString(1, String.valueOf(miner));
            preparedStatement.setString(2, String.valueOf(bucheron));
            preparedStatement.setString(3, String.valueOf(chasseur));
            preparedStatement.setString(4, playerEntity.getName().getString().toLowerCase());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void addJobs(PlayerEntity playerEntity, Map<JobsEnum, Integer> jobsIntegerMap) {
        Map<JobsEnum, Integer> defaultJobs = getJobs(playerEntity);
        int miner = jobsIntegerMap.containsKey(JobsEnum.MINER) ? jobsIntegerMap.get(JobsEnum.MINER) + defaultJobs.get(JobsEnum.MINER): defaultJobs.get(JobsEnum.MINER);
        int bucheron = jobsIntegerMap.containsKey(JobsEnum.LUMBERJACK) ? jobsIntegerMap.get(JobsEnum.LUMBERJACK) + defaultJobs.get(JobsEnum.LUMBERJACK) : defaultJobs.get(JobsEnum.LUMBERJACK);
        int chasseur = jobsIntegerMap.containsKey(JobsEnum.CHASSEUR) ? jobsIntegerMap.get(JobsEnum.CHASSEUR) + defaultJobs.get(JobsEnum.CHASSEUR) : defaultJobs.get(JobsEnum.CHASSEUR);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET miner = ?, bucheron = ?,chasseur = ? WHERE pseudo = ?");
            preparedStatement.setString(1, String.valueOf(miner));
            preparedStatement.setString(2, String.valueOf(bucheron));
            preparedStatement.setString(3, String.valueOf(chasseur));
            preparedStatement.setString(4, playerEntity.getName().getString().toLowerCase());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static BDD getInstance() {
        if (instance == null) {
            instance = new BDD();
        }
        return instance;
    }
}
