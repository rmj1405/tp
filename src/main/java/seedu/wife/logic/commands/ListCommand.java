package seedu.wife.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.wife.model.Model.PREDICATE_SHOW_ALL_FOODS;

import seedu.wife.model.Model;

/**
 * Lists all food items in WIFE to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFoodList(PREDICATE_SHOW_ALL_FOODS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
