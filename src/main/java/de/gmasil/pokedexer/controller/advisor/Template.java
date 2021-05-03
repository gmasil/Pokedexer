/**
 * Pokédexer
 * Copyright © 2021 Gmasil
 *
 * This file is part of Pokédexer.
 *
 * Pokédexer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Pokédexer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Pokédexer. If not, see <https://www.gnu.org/licenses/>.
 */
package de.gmasil.pokedexer.controller.advisor;

import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.ui.Model;

import de.gmasil.pokedexer.jpa.Card;
import de.gmasil.pokedexer.jpa.Series;

public class Template {
    public static final String INDEX = "index";
    public static final String ERROR = "error";

    public static final String APP = "app";
    public static final String SETUP = "setup";

    public static final String CATEGORY = "category";
    public static final String CATEGORY_DTO = "categoryDTO";
    public static final String CATEGORIES = "categories";

    public static final String TRANSACTION = "transaction";
    public static final String TRANSACTION_DTO = "transactionDTO";
    public static final String TRANSACTIONS = "transactions";

    public static final String CATEGORIES_CRUD = "categories-crud";
    public static final String TRANSACTIONS_CRUD = "transactions-crud";
    public static final String LIST = "list";
    public static final String ADD_EDIT = "add-edit";
    public static final String EDIT = "edit";

    private Model model;

    public Template(Model model) {
        this.model = model;
    }

    // public

    public String makeLogin() {
        setSite("login");
        return INDEX;
    }

    public String makeError(int code, String message, Exception exception) {
        setErrorArgs(code, message, exception);
        setSite(ERROR);
        return INDEX;
    }

    public String makeText(String text) {
        model.addAttribute("text", text);
        return makeApp("generic", "text");
    }

    // progress

    public String makeProgress(int value) {
        addAttribute("svgprogress", value);
        return "fragments/progress";
    }

    // series

    public String makeSeriesList(List<Series> series) {
        setSite(APP);
        setFragmentFile("series-crud");
        setFragment("list");
        addAttribute("series", series);
        return INDEX;
    }

    public String makeSeriesAdd() {
        return makeSeriesAdd(false);
    }

    public String makeSeriesAdd(boolean nameAlreadyExists) {
        setSite(APP);
        setFragmentFile("series-crud");
        setFragment(ADD_EDIT);
        setMethod("add");
        if (nameAlreadyExists) {
            addAttribute("namealreadyexists", nameAlreadyExists);
        }
        return INDEX;
    }

    public String makeSeriesEdit(Series series) {
        return makeSeriesEdit(series, false);
    }

    public String makeSeriesEdit(Series series, boolean nameAlreadyExists) {
        setSite(APP);
        setFragmentFile("series-crud");
        setFragment(ADD_EDIT);
        setMethod("edit");
        addAttribute("series", series);
        if (nameAlreadyExists) {
            addAttribute("namealreadyexists", nameAlreadyExists);
        }
        return INDEX;
    }

    public String makeSeriesDeleteConfirm(Series series) {
        setSite(APP);
        setFragmentFile("series-crud");
        setFragment("delete-confirm");
        addAttribute("series", series);
        return INDEX;
    }

    // card

    public String makeCardList(List<Card> cards) {
        setSite(APP);
        setFragmentFile("card-crud");
        setFragment("list");
        addAttribute("cards", cards);
        return INDEX;
    }

    public String makeCardAdd(List<Series> series) {
        setSite(APP);
        setFragmentFile("card-crud");
        setFragment(ADD_EDIT);
        setMethod("add");
        addAttribute("series", series);
        addAttribute("progressvalues", Card.PROGRESS_VALUES);
        return INDEX;
    }

    public String makeCardEdit(Card card, List<Series> series) {
        setSite(APP);
        setFragmentFile("card-crud");
        setFragment(ADD_EDIT);
        setMethod("edit");
        addAttribute("card", card);
        addAttribute("series", series);
        addAttribute("progressvalues", Card.PROGRESS_VALUES);
        return INDEX;
    }

    public String makeCardDeleteConfirm(Card card) {
        setSite(APP);
        setFragmentFile("card-crud");
        setFragment("delete-confirm");
        addAttribute("card", card);
        return INDEX;
    }

    // setup

    public String makeSetup() {
        setSite(SETUP);
        setFragmentFile(SETUP);
        setFragment(SETUP);
        return INDEX;
    }

    public String makeSetupAlreadyDone() {
        setSite(SETUP);
        setFragmentFile(SETUP);
        setFragment("already-setup");
        return INDEX;
    }

    // private

    private String makeApp(String fragmentfile, String fragment) {
        setSite("app");
        setFragmentFile(fragmentfile);
        setFragment(fragment);
        return INDEX;
    }

    private Model addAttribute(String attributeName, @Nullable Object attributeValue) {
        return model.addAttribute(attributeName, attributeValue);
    }

    private void setMethod(String method) {
        model.addAttribute("method", method);
    }

    private void setSite(String site) {
        model.addAttribute("site", site);
    }

    private void setFragmentFile(String site) {
        model.addAttribute("fragmentfile", site);
    }

    private void setFragment(String fragment) {
        model.addAttribute("fragment", fragment);
    }

    private void setErrorArgs(int code, String message, Exception exception) {
        addAttribute("code", code);
        addAttribute("message", message);
        addAttribute("exception", exception);
    }
}
