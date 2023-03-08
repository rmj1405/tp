package seedu.wife.model;

import static java.util.Objects.requireNonNull;
import static seedu.wife.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.wife.commons.core.GuiSettings;
import seedu.wife.commons.core.LogsCenter;
import seedu.wife.model.food.Food;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Wife wife;
    private final UserPrefs userPrefs;
    private final FilteredList<Food> filteredFoods;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyWife wife, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(wife, userPrefs);

        logger.fine("Initializing with Wife: " + wife + " and user prefs " + userPrefs);

        this.wife = new Wife(wife);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFoods = new FilteredList<>(this.wife.getFoodList());
    }

    public ModelManager() {
        this(new Wife(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getWifeFilePath() {
        return userPrefs.getWifeFilePath();
    }

    @Override
    public void setWifeFilePath(Path wifeFilePath) {
        requireNonNull(wifeFilePath);
        userPrefs.setWifeFilePath(wifeFilePath);
    }

    //=========== Wife ================================================================================

    @Override
    public void setWife(ReadOnlyWife wife) {
        this.wife.resetData(wife);
    }

    @Override
    public ReadOnlyWife getWife() {
        return wife;
    }

    @Override
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return wife.hasFood(food);
    }

    @Override
    public void deleteFood(Food target) {
        wife.removeFood(target);
    }

    @Override
    public void addFood(Food food) {
        wife.addFood(food);
        updateFilteredFoodList(PREDICATE_SHOW_ALL_FOODS);
    }

    @Override
    public void setFood(Food target, Food editedFood) {
        requireAllNonNull(target, editedFood);

        wife.setFood(target, editedFood);
    }

    //=========== Filtered Food List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Food} backed by the internal list of
     * {@code versionedWife}
     */
    @Override
    public ObservableList<Food> getFilteredFoodList() {
        return filteredFoods;
    }

    @Override
    public void updateFilteredFoodList(Predicate<Food> predicate) {
        requireNonNull(predicate);
        filteredFoods.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return wife.equals(other.wife)
                && userPrefs.equals(other.userPrefs)
                && filteredFoods.equals(other.filteredFoods);
    }

}
