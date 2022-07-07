package com.orderProcessing.Restaurant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.orderProcessing.Restaurant.dataAccess.DAO;
import com.orderProcessing.Restaurant.model.Dish;
import com.orderProcessing.Restaurant.model.Menu;
import com.orderProcessing.Restaurant.model.Staff;

@Service
public class MenuService {
	private List<Menu> menus;
	private List<Dish> dishesFromMenus;
	private final DAO<Menu> menuDAO;
	private final DAO<Dish> dishDAO;

	public MenuService(DAO<Menu> menuDAO, DAO<Dish> dishDAO) {
		this.menuDAO = menuDAO;
		this.dishDAO = dishDAO;
		menus = new ArrayList<>();
		dishesFromMenus = new ArrayList<>();
		loadMenus();
		loadDishes();
		// getAllDishesByMenus();
	}

	public void addMenu(Menu menu) {
		menuDAO.add(menu);
		loadMenus();
	}

	public void editMenu(int id, Menu menu) {
		Menu menuToEdit = findMenu(id);
		menuToEdit.setName(menu.getName());
		menuToEdit.setDescription(menu.getDescription());
		menuToEdit.setActive(menu.getActive());
		menuToEdit.setType(menu.getType());
		menuToEdit.setStartDate(menu.getStartDate());
		menuToEdit.setEndDate(menu.getEndDate());
		menuDAO.update(menuToEdit);
	}

	public void editMenu(String name, Menu menu) {
		Menu menuToEdit = findMenu(name);
		menuToEdit.setName(menu.getName());
		menuToEdit.setDescription(menu.getDescription());
		menuToEdit.setActive(menu.getActive());
		menuToEdit.setType(menu.getType());
		menuToEdit.setStartDate(menu.getStartDate());
		menuToEdit.setEndDate(menu.getEndDate());
		menuDAO.update(menuToEdit);
	}

	public Menu findMenu(int id) {
		Menu menuToReturn = null;
		for (Menu menu : menus) {
			if (menu.getId() == id)
				menuToReturn = menu;
		}
		return menuToReturn;
	}

	public Menu findMenu(String name) {
		Menu menuToReturn = null;
		for (Menu menu : menus) {
			if (menu.getName().equals(name))
				menuToReturn = menu;
		}
		return menuToReturn;
	}

	public List<Menu> getAllMenus() {
		return menus;
	}

	public void removeMenu(int id) {
		Menu menuToDelete = findMenu(id);
		menus.remove(menuToDelete);
		for (Dish dish : menuToDelete.getDishes()) {
			removeDish(dish.getId());
		}
		menuDAO.delete(menuToDelete);
		loadMenus();
	}

	public void removeMenu(String name) {
		Menu menuToDelete = findMenu(name);
		menuDAO.delete(menuToDelete.getId());
	}

	public void loadMenus() {
		menus = menuDAO.getAll();
	}

	public void addDishToMenu(int id, Dish dish) {
		Menu menuToAdd = findMenu(id);
		dishDAO.add(dish);
		loadDishes();
		System.out.println(dishesFromMenus);
		Dish dishLoaded = findDish(dish.getName());
		System.out.println(dishLoaded.getId());
		menuToAdd.getDishes().add(dishLoaded);
		menuDAO.update(menuToAdd);
		loadMenus();
	}

	public void removeDishInMenu(Dish dish) {
		for (Menu menu : menus) {
			for (Dish d : menu.getDishes()) {
				if (d.getId() == dish.getId()) {
					menu.getDishes().remove(d);
				}
			}

		}
	}

	public Dish findDishInMenus(Dish dish) {
		Dish dishRetun = null;
		for (Menu menu : menus) {
			for (Dish d : menu.getDishes()) {
				if (d.getId() == dish.getId()) {
					dishRetun = d;
				}

			}
		}
		return dishRetun;
	}

	public void addDishToMenu(int id, int dishId) {
		Menu menuToAdd = findMenu(id);
		menuToAdd.getDishes().add(dishDAO.getById(dishId));

		// finish addition
	}

	public void addDish(Dish dish) {
		dishDAO.add(dish);
	}

	public void editDish(Dish dish) {
		Dish toEdit = findDishInMenus(dish);
		toEdit.setAlergens(dish.getAlergens());
		toEdit.setCategory(dish.getCategory());
		toEdit.setName(dish.getName());
		toEdit.setPrice(dish.getPrice());
		toEdit.setDescription(dish.getDescription());
		toEdit.setType(dish.getType());
		toEdit.setIngridients(dish.getIngridients());
		dishDAO.update(toEdit);
	}

	public void removeDish(int id) {
		Dish toRemove = findDish(id);
		System.out.println(toRemove);
		dishesFromMenus.remove(toRemove);
		// removeDishInMenu(toRemove);
		dishDAO.delete(toRemove);
		loadMenus();
	}

	public void addDish(int id, int dishId) {
		Menu menuToAdd = findMenu(id);
		menuToAdd.getDishes().add(dishDAO.getById(dishId));
	}

	public Dish findDish(int id) {
		getAllDishesByMenus();
		Dish dishToReturn = null;
		for (Dish dish : dishesFromMenus) {
			if (dish.getId() == id)
				dishToReturn = dish;
		}
		return dishToReturn;
	}

	public Dish findDish(String name) {
		Dish dishToReturn = null;
		for (Dish dish : dishesFromMenus) {
			if (dish.getName().equals(name))
				dishToReturn = dish;
		}
		return dishToReturn;
	}

	public void getAllDishesByMenus() {
		for (Menu menu : menus) {
			dishesFromMenus.addAll(menu.getDishes());
		}
	}

	public List<Dish> getAllDishes() {
		return dishesFromMenus;
	}

	public void loadDishes() {
		dishesFromMenus = dishDAO.getAll();
	}

}
