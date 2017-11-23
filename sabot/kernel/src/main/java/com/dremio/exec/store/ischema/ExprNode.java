/*
 * Copyright (C) 2017 Dremio Corporation
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
package com.dremio.exec.store.ischema;

import com.dremio.exec.store.ischema.InfoSchemaFilter.ConstantExprNode;
import com.dremio.exec.store.ischema.InfoSchemaFilter.FieldExprNode;
import com.dremio.exec.store.ischema.InfoSchemaFilter.FunctionExprNode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "typeName")
@JsonSubTypes({
  @JsonSubTypes.Type(value = FunctionExprNode.class),
  @JsonSubTypes.Type(value = FieldExprNode.class),
  @JsonSubTypes.Type(value = ConstantExprNode.class)
  })
public class ExprNode {

  private final ExprNode.Type type;

  protected ExprNode(ExprNode.Type type) {
    this.type = type;
  }

  @JsonIgnore
  public ExprNode.Type getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExprNode exprNode = (ExprNode) o;
    return type == exprNode.type;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(type);
  }

  public enum Type {
    FUNCTION,
    FIELD,
    CONSTANT
  }
}