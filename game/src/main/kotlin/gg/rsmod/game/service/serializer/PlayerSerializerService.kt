package gg.rsmod.game.service.serializer

import de.mkammerer.argon2.Argon2Factory
import gg.rsmod.game.Server
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.NEW_ACCOUNT_ATTR
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.service.Service
import gg.rsmod.net.codec.login.LoginRequest
import gg.rsmod.util.ServerProperties

/**
 * A [Service] that is responsible for encoding and decoding player data.
 *
 * @author Tom <rspsmods@gmail.com>
 */
abstract class PlayerSerializerService : Service {

    private lateinit var startTile: Tile

    final override fun init(server: Server, world: World, serviceProperties: ServerProperties) {
        startTile = Tile(3680, 4941, 0)
        initSerializer(server, world, serviceProperties)
    }

    override fun postLoad(server: Server, world: World) {
    }

    override fun bindNet(server: Server, world: World) {
    }

    override fun terminate(server: Server, world: World) {
    }

    fun configureNewPlayer(client: Client, request: LoginRequest) {
        client.attr.put(NEW_ACCOUNT_ATTR, true)

        client.passwordHash = Argon2Factory.create().hash(2, 65536, 1, request.password.toCharArray())
        client.tile = startTile
    }

    abstract fun initSerializer(server: Server, world: World, serviceProperties: ServerProperties)

    abstract fun loadClientData(client: Client, request: LoginRequest): PlayerLoadResult

    abstract fun saveClientData(client: Client): Boolean
}