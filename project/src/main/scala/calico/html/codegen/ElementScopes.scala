/*
 * Copyright 2022 Arman Bilge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package calico.html.codegen

/**
 * Maps HTML attributes and properties to the element types they are valid on.
 *
 * fs2-dom provides specific types for:
 *   - HtmlElement[F] (base for all HTML elements)
 *   - HtmlAnchorElement[F]
 *   - HtmlButtonElement[F]
 *   - HtmlInputElement[F]
 *   - HtmlOptionElement[F]
 *   - HtmlSelectElement[F]
 *   - HtmlTextAreaElement[F]
 *
 * Attributes not listed here are considered global (valid on all HtmlElement[F]).
 */
private[codegen] object ElementScopes {

  /** fs2-dom element types that have specific subtypes */
  val specificElementTypes: Set[String] = Set(
    "HtmlAnchorElement",
    "HtmlButtonElement",
    "HtmlInputElement",
    "HtmlOptionElement",
    "HtmlSelectElement",
    "HtmlTextAreaElement"
  )

  /**
   * Maps an HTML attribute's Scala name to the set of specific element type names it applies to.
   * Attributes NOT in this map are global (apply to all HtmlElement subtypes).
   * Element types listed here correspond to fs2-dom types (without [F]).
   *
   * Note: only actual HTML attributes (from scala-dom-types AttrDef) belong here.
   * DOM properties (from PropDef) belong in propScopes below.
   */
  val attrScopes: Map[String, Set[String]] = Map(
    "href" -> Set("HtmlAnchorElement"),
    "formId" -> Set("HtmlInputElement", "HtmlButtonElement", "HtmlSelectElement", "HtmlTextAreaElement"),
    "maxAttr" -> Set("HtmlInputElement"),
    "minAttr" -> Set("HtmlInputElement"),
    "stepAttr" -> Set("HtmlInputElement")
  )

  /**
   * Maps a property's Scala name to the set of specific element type names it applies to.
   * Properties NOT in this map are global.
   */
  val propScopes: Map[String, Set[String]] = Map(
    "checked" -> Set("HtmlInputElement"),
    "indeterminate" -> Set("HtmlInputElement"),
    "defaultChecked" -> Set("HtmlInputElement"),
    "defaultValue" -> Set("HtmlInputElement", "HtmlTextAreaElement"),
    "value" -> Set("HtmlInputElement", "HtmlTextAreaElement", "HtmlSelectElement", "HtmlOptionElement"),
    "selected" -> Set("HtmlOptionElement"),
    "multiple" -> Set("HtmlInputElement", "HtmlSelectElement"),
    "disabled" -> Set("HtmlInputElement", "HtmlButtonElement", "HtmlSelectElement", "HtmlTextAreaElement", "HtmlOptionElement"),
    "readOnly" -> Set("HtmlInputElement", "HtmlTextAreaElement")
  )

  /**
   * Returns the fs2-dom element bound for a given attribute/property scope.
   * If the attr/prop is global, returns "HtmlElement".
   * If scoped to specific elements, returns each element type.
   */
  def elementBoundsForAttr(scalaName: String): List[String] =
    attrScopes.get(scalaName) match {
      case Some(elements) => elements.toList.sorted
      case None => List("HtmlElement")
    }

  def elementBoundsForProp(scalaName: String): List[String] =
    propScopes.get(scalaName) match {
      case Some(elements) => elements.toList.sorted
      case None => List("HtmlElement")
    }
}
