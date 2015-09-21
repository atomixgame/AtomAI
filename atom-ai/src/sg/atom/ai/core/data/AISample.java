/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.core.data;

/**
 * Listenable Result of "sampling" operation over AIData.
 *
 * <p>The promised sampling can be left raw (or unfinish) at the progress, but
 * the delta are recognized. Sample is a kind of "processive" data or
 * "incremental" data, deepening and grow over time very suite for AI!
 *
 * <p>This inspired by numberous of articles on the internet and the Delta
 * concept of ThreeRings and Arriane (and other MMO fw).
 *
 * <p>This feature enabled by Guava's Future and Delta concept. Read this for
 * inspiration:
 * http://www.somic.org/2009/06/25/full-data-vs-incremental-data-in-messaging/
 *
 * @author hungcuong
 */
public class AISample {
}
