/*******************************************************************************
 * Copyright (c) 2013, Salesforce.com, Inc.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *     Neither the name of Salesforce.com nor the names of its contributors may 
 *     be used to endorse or promote products derived from this software without 
 *     specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE 
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package com.salesforce.phoenix.parse;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.salesforce.phoenix.schema.PDataType;



/**
 * 
 * Node representing literal expressions such as 1,2.5,'foo', and NULL in SQL
 *
 * @author jtaylor
 * @since 0.1
 */
public class LiteralParseNode extends TerminalParseNode {
    public static final List<ParseNode> STAR = Collections.<ParseNode>singletonList(new LiteralParseNode(1));
    public static final ParseNode NULL = new LiteralParseNode(null);
    public static final ParseNode ZERO = new LiteralParseNode(0);
    public static final ParseNode ONE = new LiteralParseNode(1);
    
    private final Object value;
    private final PDataType type;
    
    public LiteralParseNode(Object value) {
        this.value = value;
        this.type = PDataType.fromLiteral(value);
    }

    public PDataType getType() {
        return type;
    }
    
    public Object getValue() {
        return value;
    }

    @Override
    public boolean isStateless() {
        return true;
    }
    
    @Override
    public <T> T accept(ParseNodeVisitor<T> visitor) throws SQLException {
        return visitor.visit(this);
    }

    public byte[] getBytes() {
        return type == null ? null : type.toBytes(value);
    }
    
    @Override
    public String toString() {
        return type == PDataType.VARCHAR ? ("'" + value.toString() + "'") : value == null ? "null" : value.toString();
    }
}
