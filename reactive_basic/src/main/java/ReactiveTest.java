import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReactiveTest {

    public static void main(String[] args){
        //List<Integer> elements = new ArrayList<>();

//        Flux.just(1, 2, 3, 4)
//                .log()
//                .map(i -> i * 2)
//                .subscribe(elements::add);
//        assertThat(elements).containsExactly(2,4,6,8);
//
//

//        Flux<String> flux = Flux.generate(sink -> {
//            sink.next("Hello");
//            sink.complete();
//        });


//        Flux<Integer> a = Flux.create(sink -> {
//            for (int i = 0; i < 5; i++) {
//                sink.next(i);
//            }
//            sink.complete();
//        });
//
//
//        a.log().subscribe();


        List<Integer> elements = new ArrayList<>();
        Executor executor = Executors.newSingleThreadExecutor();
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .subscribeOn(Schedulers.fromExecutor(executor))
                .subscribe(elements::add);

        assertThat(elements).containsExactly(2,4,6,8);


    }
}
