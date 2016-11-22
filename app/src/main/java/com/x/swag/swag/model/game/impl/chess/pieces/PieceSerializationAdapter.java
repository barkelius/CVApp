package com.x.swag.swag.model.game.impl.chess.pieces;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.BishopPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.EmptyPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.KingPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.KnightPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.OutOfBoardPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.PawnPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.QueenPiece;
import com.x.swag.swag.model.game.impl.chess.pieces.impl.RookPiece;

import java.lang.reflect.Type;

/**
 * Created by barke on 2016-04-09.
 */
public class PieceSerializationAdapter implements JsonSerializer<AbstractChessPiece>, JsonDeserializer<AbstractChessPiece> {

    private static final String VALUE = "VALUE";
    private static final String TEAM = "TEAM";
    private static final String INSTANCE  = "INSTANCE";

    @Override
    public JsonElement serialize(AbstractChessPiece piece, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject retValue = new JsonObject();
        retValue.addProperty(VALUE, piece.value());
        retValue.addProperty(TEAM, piece.getTeam());
        JsonElement elem = context.serialize(piece);
        retValue.add(INSTANCE, elem);
        return retValue;
    }

    @Override
    public AbstractChessPiece deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject =  json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(VALUE);
        int pieceValue = prim.getAsInt();
        boolean white = jsonObject.get(TEAM).getAsInt() == 1;
        Class<?> clazz = null;
        switch (pieceValue){
            case 1:
                return white ? PawnPiece.white() : PawnPiece.black();
            case 2:
                clazz = KnightPiece.class;
                break;
            case 3:
                clazz = BishopPiece.class;
                break;
            case 4:
                clazz = RookPiece.class;
                break;
            case 5:
                clazz = QueenPiece.class;
                break;
            case 7:
                clazz = KingPiece.class;
                break;
            default:
                throw new IllegalArgumentException("WRONG TYPE!");
        }
        return context.deserialize(jsonObject.get(INSTANCE), clazz);
    }
}