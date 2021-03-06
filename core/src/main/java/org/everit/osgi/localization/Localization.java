/**
 * This file is part of Everit - Localization.
 *
 * Everit - Localization is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Everit - Localization is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Everit - Localization.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.everit.osgi.localization;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * A service that makes it possible to store localized key-value pairs. Keys and values have the type
 */
public interface Localization {

    /**
     * Saving a new String based localized data with the given key and locale. If there was no value for the specified
     * key before, this value will be the default.
     *
     * @param key
     *            The key of the data.
     * @param locale
     *            The locale of the data.
     * @param value
     *            The value of the data. If length more then 2000 throw IllegalArgumentException.
     * @return The created localized data.
     * @throws NullPointerException
     *             if the key, locale or value is null
     * @throws IllegalArgumentException
     *             if value is longer than 2000.
     */
    void addValue(String key, Locale locale, String value);

    /**
     * Clears the cache of the store. This should be only necessary if another module does a bulk update (e.g.: updating
     * default locale for a set of records) that cannot be done via the API of this module.
     */
    void clearCache();

    /**
     * Returns the value for the specified key with the specified locale.
     *
     * @param key
     *            The key of the localized entry.
     * @param locale
     *            The locale of the entry.
     * @return The value of the key and locale or {@link Optional#empty()} if there is no value for the specified
     *         key-locale pair.
     */
    Optional<String> getExactValue(String key, Locale locale);

    /**
     * Getting the list of locals that are available for the given key.
     *
     * @param key
     *            The key of the localized data.
     * @return The available locals.
     */
    Locale[] getSupportedLocalesForKey(String key);

    /**
     * Same as {@link #getValue(String, Locale, String)}. TODO
     */
    String getValue(String key, Locale locale);

    /**
     * Getting a localized string by key the and locale. If the requested string is not available in the specified
     * locale, the locale will be cropped in the same way as {@link ResourceBundle} does. E.g.: If the locale en_GB is
     * queried, first the original locale is checked, than the 'en' than the default locale. If non of them exists, the
     * key is returned.
     *
     * @param key
     *            The key of the localized data.
     * @param locale
     *            Null means default locale.
     * @param defaultValue
     *            the value that is returned if no entry found.
     * @return The localized data. If there is no data based on the given locale the default locale will be checked in
     *         the same way as it is done in {@link java.util.ResourceBundle}. If there is no default locale, the
     *         defaultValue parameter is returned.
     * @throws NullPointerException
     *             if key is null.
     */
    String getValue(String key, Locale locale, String defaultValue);

    /**
     * Removes all values that belong the a specific key.
     *
     * @param key
     *            The key.
     * @return The number of records that were deleted. In other words: The number of locales that were specified for
     *         the key.
     * @throws NullPointerException
     *             if key is null.
     */
    long removeKey(String key);

    /**
     * Removes a localized value from the store. If there is no such value, the function has no effect.
     *
     * @param key
     *            The key of the localized value.
     * @param locale
     *            The {@link Locale} of the value.
     * @throws NullPointerException
     *             if the key or locale parameter is <code>null</code>.
     * @throws DeletingNotAllowedException
     *             if the locale is the default locale and there are other non-default values for the same key. In that
     *             case it could not be decided, which locale should be the default one.
     */
    void removeValue(String key, Locale locale);

    /**
     * Switching the default locale of a key to a new one.
     *
     * @param key
     *            The key of the localized entry.
     * @param locale
     *            The new default locale.
     * @throws EntryDoesNotExistException
     *             if key-locale based value does not exist.
     */
    void switchDefaultLocale(String key, Locale locale);

    /**
     * Updating an existing String based localized data. The localized data is identified by its key and locale.
     *
     * @param key
     *            The key of the data. If null throw IllegalArgumentException
     * @param locale
     *            The locale of the data. If null throw IllegalArgumentException
     * @param value
     *            The value of the data. Maximum length is 2000 if more throw IllegalArgumentException.
     * @return The number of rows affected during update.
     * @throws NullPointerException
     *             if the key, locale or value is null
     * @throws IllegalArgumentException
     *             if value is longer than 2000.
     */
    void updateValue(String key, Locale locale, String value);
}
