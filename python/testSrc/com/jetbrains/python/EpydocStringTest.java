package com.jetbrains.python;

import com.jetbrains.python.documentation.EpydocString;
import junit.framework.TestCase;

/**
 * @author yole
 */
public class EpydocStringTest extends TestCase {
  public void testTagValue() {
    EpydocString docString = new EpydocString("@rtype: C{str}");
    assertEquals("C{str}", docString.getTagValue("rtype"));
  }

  public void testTagWithParamValue() {
    EpydocString docString = new EpydocString("@type m: number");
    assertEquals("number", docString.getTagValue("type", "m"));
  }

  public void testMultilineTag() {
    EpydocString docString = new EpydocString("    @param b: The y intercept of the line.  The X{y intercept} of a\n" +
                                              "              line is the point at which it crosses the y axis (M{x=0}).");
    assertEquals("The y intercept of the line.  The X{y intercept} of a line is the point at which it crosses the y axis (M{x=0}).",
                 docString.getTagValue("param", "b"));

  }

  public void testInlineMarkup() {
    assertEquals("The y intercept of the line.  The y intercept of a line is the point at which it crosses the y axis (x=0).",
                 EpydocString.removeInlineMarkup("The y intercept of the line.  The X{y intercept} of a line is the point at which it crosses the y axis (M{x=0})."));

  }

  public void testMultipleTags() {
    EpydocString docString = new EpydocString("    \"\"\"\n" +
                                              "    Run the given function wrapped with seteuid/setegid calls.\n" +
                                              "\n" +
                                              "    This will try to minimize the number of seteuid/setegid calls, comparing\n" +
                                              "    current and wanted permissions\n" +
                                              "\n" +
                                              "    @param euid: effective UID used to call the function.\n" +
                                              "    @type  euid: C{int}\n" +
                                              "\n" +
                                              "    @param egid: effective GID used to call the function.\n" +
                                              "    @type  egid: C{int}\n" +
                                              "\n" +
                                              "    @param function: the function run with the specific permission.\n" +
                                              "    @type  function: any callable\n" +
                                              "\n" +
                                              "    @param *args: arguments passed to function\n" +
                                              "    @param **kwargs: keyword arguments passed to C{function}\n" +
                                              "    \"\"\"");
    assertEquals("effective UID used to call the function.", docString.getParamDescription("euid"));
    assertEquals("effective GID used to call the function.", docString.getParamDescription("egid"));
    assertEquals("arguments passed to function", docString.getParamDescription("args"));
  }
}
