package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class FamilysFavor extends CardImpl {

    public FamilysFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever you attack, put a shield counter on target attacking creature.
        // Until end of turn, it gains
        //      “Whenever this creature deals combat damage to a player,
        //       remove a shield counter from it.
        //       If you do, draw a card.”
        Ability attacksAbility = new AttacksWithCreaturesTriggeredAbility(new AddCountersTargetEffect(CounterType.SHIELD.createInstance()), 1);
        attacksAbility.addEffect(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(new FamilysFavorRemoveShieldDrawEffect(), false),
                Duration.EndOfTurn,
                "Until end of turn, it gains " +
                        "\"Whenever this creature deals combat damage to a player, remove a shield counter from it. " +
                        "If you do, draw a card.\""
                ).setText("Until end of turn, it gains " +
                            "\"Whenever this creature deals combat damage to a player, remove a shield counter from it. " +
                            "If you do, draw a card.\" " +
                          "<i>(If a creature with a shield counter on it would be dealt damage or destroyed, remove a shield counter from it instead.)</i>"
                )
        );

        attacksAbility.addTarget(new TargetAttackingCreature());
        this.addAbility(attacksAbility);
    }

    private FamilysFavor(final FamilysFavor card) {
        super(card);
    }

    @Override
    public FamilysFavor copy() {
        return new FamilysFavor(this);
    }
}

class FamilysFavorRemoveShieldDrawEffect extends OneShotEffect {

    FamilysFavorRemoveShieldDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "remove a shield counter from it. If you do, draw a card.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent attacker = game.getPermanent(source.getSourceId());
        if (controller == null || attacker == null) {
            return false;
        }

        Counter tmpShieldCounter = CounterType.SHIELD.createInstance();
        Counter shieldCountersOnAttacker = attacker.getCounters(game).get(tmpShieldCounter.getName());
        if (shieldCountersOnAttacker == null || shieldCountersOnAttacker.getCount() == 0) {
            return false;
        }

        attacker.removeCounters(tmpShieldCounter.getName(), 1, source, game);
        game.applyEffects();
        controller.drawCards(1, source, game);
        return true;
    }

    FamilysFavorRemoveShieldDrawEffect(final FamilysFavorRemoveShieldDrawEffect effect) {
        super(effect);
    }

    @Override
    public Effect copy() {
        return new FamilysFavorRemoveShieldDrawEffect(this);
    }
}