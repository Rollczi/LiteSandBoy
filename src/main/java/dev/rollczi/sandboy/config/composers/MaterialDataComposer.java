package dev.rollczi.sandboy.config.composers;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import panda.std.Result;

public class MaterialDataComposer implements LiteComposer<MaterialData> {

    @Override
    public Result<MaterialData, Exception> deserialize(String dataString) {
        String[] data = dataString.split(":");

        if (data.length != 2) {
            return Result.error(new UnsupportedOperationException("Cannot deserialize " + dataString));
        }

        return Result.ok(new MaterialData(Material.getMaterial(data[0]), Byte.parseByte(data[1])));
    }

    @Override
    public Result<String, Exception> serialize(MaterialData materialData) {
        return Result.ok(materialData.getItemType() + ":" + materialData.getData());
    }

}
